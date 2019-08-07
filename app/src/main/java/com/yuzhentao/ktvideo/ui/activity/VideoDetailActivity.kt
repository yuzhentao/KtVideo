package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTask
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.util.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_video_detail.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream

/**
 * 视频详情
 */
class VideoDetailActivity : AppCompatActivity() {

    private var context: Context = this
    private var activity: VideoDetailActivity = this

    private lateinit var bean: VideoBean

    private lateinit var ivCover: ImageView//封面
    private var coverDisposable: Disposable? = null

    private lateinit var orientationUtils: OrientationUtils//处理屏幕旋转的逻辑

    private lateinit var dbManager: VideoDbManager
    private var dbBean: VideoBean? = null

    private var playUrl: String? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.black)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_video_detail)
        dbManager = VideoDbManager()
        Aria.download(this).register()
        val bundle = intent.getBundleExtra("bundle")
        bean = bundle.getParcelable("data")
        initView()
        prepareVideo()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        dbManager.close()
        coverDisposable.let {
            if (!coverDisposable!!.isDisposed) {
                coverDisposable!!.dispose()
            }
        }
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils.let {
            orientationUtils.releaseListener()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!vp.isIfCurrentIsFullscreen) {
                    vp.startWindowFullscreen(context, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (vp.isIfCurrentIsFullscreen) {
                    StandardGSYVideoPlayer.backFromWindowFull(context)
                }
                orientationUtils.let {
                    orientationUtils.isEnable = true
                }
            }
        }
    }

    override fun onBackPressed() {
        orientationUtils.let {
            orientationUtils.backToProtVideo()
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(context)) {
            return
        }
        super.onBackPressed()
    }

    private fun initView() {
        playUrl = bean.playUrl
        val blurred = bean.blurred//接口提供的虚化图
        blurred?.let {
            ImageUtil.displayHigh(context, iv_bg, blurred)
        }
        tv_title.text = bean.title
        tv_title.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        tv_desc.text = bean.description
        tv_desc.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_desc.movementMethod = ScrollingMovementMethod.getInstance()
        val category = bean.category
        val duration = bean.duration
        val minute = duration?.div(60)
        val second = minute?.times(60)?.let {
            duration.minus(it)
        }
        val realMinute: String
        val realSecond: String
        realMinute = if (minute!! < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        realSecond = if (second!! < 10) {
            "0$second"
        } else {
            second.toString()
        }
        val time = "发布于 $category / $realMinute:$realSecond"
        tv_time.text = time
        tv_favorite.text = bean.collect.toString()
        tv_share.text = bean.share.toString()
        tv_reply.text = bean.reply.toString()
        ll_download.visibility = if (intent.getBooleanExtra("showCache", true)) View.VISIBLE else View.GONE
        tv_download.setOnClickListener {
            playUrl?.let {
                if (dbManager.find(playUrl!!) == null) {
                    bean.id?.let {
                        cache(bean.id!!)
                    }
                } else {
                    dbBean = dbManager.find(playUrl!!)
                    when (dbBean!!.downloadState) {
                        DownloadState.NORMAL.name -> {
                            bean.id?.let {
                                cache(bean.id!!)
                            }
                        }
                        DownloadState.DOWNLOADING.name -> shortToast(getString(R.string.cache_ing))
                        DownloadState.PAUSE.name -> shortToast(getString(R.string.cache_ing))
                        DownloadState.COMPLETE.name -> shortToast(getString(R.string.cache_complete))
                        else -> shortToast(getString(R.string.cache_fail))
                    }
                }
            }
        }
        playUrl?.let {
            if (dbManager.find(playUrl!!) == null) {
                dbManager.insert(bean)
            }
        }
    }

    /**
     * 缓存
     */
    private fun cache(name: Int) {
        Aria.download(this)
                .load(playUrl!!)
                .setFilePath(Environment.getExternalStorageDirectory().absolutePath + File.separator + "KtVideo" + File.separator + "download_$name.mp4")
                .start()
    }

    private fun prepareVideo() {
        val uri = intent.getStringExtra("loac©lFile")
        if (uri != null) {
            vp.setUp(uri, false, null, null)
        } else {
            vp.setUp(bean.playUrl, false, null, null)
        }
        ivCover = ImageView(context)
        ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
        setCover()
        vp.titleTextView.visibility = View.GONE
        vp.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(activity, vp)
        vp.setIsTouchWiget(true)
        vp.isRotateViewAuto = false
        vp.isLockLand = false
        vp.isShowFullAnimation = false
        vp.isNeedLockFull = true
        vp.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()//直接横屏
            vp.startWindowFullscreen(context, true, true)//第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        }
        vp.setStandardVideoAllCallBack(object : VideoListener() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                orientationUtils.isEnable = true//开始播放了才能旋转和全屏
                isPlay = true
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils.let {
                    orientationUtils.backToProtVideo()
                }
            }
        })
        vp.setLockClickListener { _, lock ->
            orientationUtils.isEnable = !lock//配合下方的onConfigurationChanged
        }
        vp.backButton.setOnClickListener {
            onBackPressed()
        }
        if (intent.getBooleanExtra("autoPlay", false)) {
            vp.startPlayLogic()
        }
    }

    private fun setCover() {
        val observable: Observable<Bitmap> = Observable.create { emitter ->
            val cacheFile = GlideApp
                    .with(context)
                    .downloadOnly()
                    .load(bean.feed)
                    .submit()
                    .get()
            val path: String? = cacheFile.absolutePath
            val inputStream: FileInputStream? = FileInputStream(path)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            emitter.onNext(bitmap)
        }
        observable.normalSchedulers().subscribe(object : Observer<Bitmap> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                coverDisposable = d
            }

            override fun onNext(bitmap: Bitmap) {
                ivCover.setImageBitmap(bitmap)
                vp.setThumbImageView(ivCover)
            }

            override fun onError(e: Throwable) {

            }
        })
    }

    @Download.onTaskStart
    fun onDownloadStart(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("缓存").e("开始>>>${bean.title}")
            shortToast(getString(R.string.cache_start))
            bean.downloadState = DownloadState.DOWNLOADING.name
            dbManager.update(bean)
        }
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("缓存").e("进度>>>${task.percent}>>>${bean.title}")
            bean.downloadProgress = task.percent
            dbManager.update(bean)
        }
    }

    @Download.onTaskFail
    fun onDownloadFail(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("缓存").e("失败>>>${bean.title}")
            shortToast(getString(R.string.cache_fail))
            bean.downloadState = DownloadState.ERROR.name
            bean.downloadProgress = 0
            dbManager.update(bean)
        }
    }

    @Download.onTaskComplete
    fun onDownloadComplete(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("缓存").e("完成>>>${bean.title}")
            shortToast(getString(R.string.cache_complete))
            bean.downloadState = DownloadState.COMPLETE.name
            bean.downloadProgress = 100
            dbManager.update(bean)
        }
    }

}