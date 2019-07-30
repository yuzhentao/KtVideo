package com.yuzhentao.ktvideo.ui.activity

import android.annotation.SuppressLint
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
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.util.*
import io.reactivex.Observable
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
        playUrl?.let {
            dbManager.find(playUrl!!)?.let {
                dbBean = dbManager.find(playUrl!!)
                if (dbBean!!.downloadState == DownloadState.COMPLETE.name) {
                    ll_download.visibility = View.GONE
                }
            }
        }
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
        tv_download.setOnClickListener {
            playUrl?.let {
                SPUtils.getInstance(context, "downloads").getString(it)//是否缓存当前视频
                if (playUrl == "") {
                    var count = SPUtils.getInstance(context, "downloads").getInt("count")
                    count = if (count != -1) {
                        count.inc()
                    } else {
                        1
                    }
                    SPUtils.getInstance(context, "downloads").put("count", count)//缓存的视频对象数量
                    ObjectSaveUtils.saveObject(context, "download$count", bean)//保存视频对象，缓存中会使用到
                    cache(count)
                } else {
                    showToast("该视频已经缓存过了")
                }
            }
        }
    }

    /**
     * 缓存
     */
    @SuppressLint("CheckResult")
    private fun cache(count: Int) {
        playUrl?.let {
            Aria.download(this)
                    .load(playUrl!!)
                    .setFilePath(Environment.getExternalStorageDirectory().absolutePath + File.separator + "KtVideo" + File.separator + "download$count.mp4")
                    .start()
        }
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
        coverDisposable = observable.normalSchedulers().subscribe { bitmap: Bitmap? ->
            ivCover.setImageBitmap(bitmap)
            vp.setThumbImageView(ivCover)
        }
    }

    @Download.onTaskStart
    fun onDownloadStart(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("下载").e("开始>>>")
            showToast("开始下载")
            SPUtils.getInstance(context, "downloads").put(bean.playUrl.toString(), bean.playUrl.toString())
            SPUtils.getInstance(context, "download_state").put(playUrl.toString(), true)
            bean.downloadState = DownloadState.DOWNLOADING.name
            dbManager.insert(bean)
        }
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("下载").e("进度>>>${task.percent}")
            bean.downloadProgress = task.percent
            dbManager.update(bean)
        }
    }

    @Download.onTaskFail
    fun onDownloadFail(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("下载").e("失败>>>")
            showToast("下载失败")
            bean.downloadState = DownloadState.ERROR.name
            bean.downloadProgress = 0
            dbManager.update(bean)
        }
    }

    @Download.onTaskComplete
    fun onDownloadComplete(task: DownloadTask) {
        if (task.key == playUrl) {
            Timber.tag("下载").e("完成>>>")
            showToast("下载完成")
            bean.downloadState = DownloadState.COMPLETE.name
            bean.downloadProgress = 100
            dbManager.update(bean)
        }
    }

}