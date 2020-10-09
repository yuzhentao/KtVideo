package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.VideoRelatedAdapter
import com.yzt.ktvideo.bean.VideoBean
import com.yzt.ktvideo.bean.VideoRelatedBean
import com.yzt.ktvideo.db.VideoDbManager
import com.yzt.ktvideo.extension.color
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.extension.shortToast
import com.yzt.ktvideo.key.Constant.KT_VIDEO
import com.yzt.ktvideo.mvp.contract.VideoRelatedContract
import com.yzt.ktvideo.mvp.presenter.VideoRelatedPresenter
import com.yzt.ktvideo.util.*
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
class VideoDetailActivity : AppCompatActivity(), VideoRelatedContract.View {

    private var context: Context = this
    private var activity: VideoDetailActivity = this

    private var bean: VideoBean? = null

    private lateinit var ivCover: ImageView//封面
    private var coverDisposable: Disposable? = null

    private lateinit var orientationUtils: OrientationUtils//处理屏幕旋转的逻辑

    private val adapter: VideoRelatedAdapter by lazy {
        VideoRelatedAdapter(null)
    }
    private val presenter: VideoRelatedPresenter by lazy {
        VideoRelatedPresenter(context, this)
    }
    private val dbManager: VideoDbManager by lazy {
        VideoDbManager()
    }
    private var dbBean: VideoBean? = null

    private var playUrl: String? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.black)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_video_detail)
        Aria.download(this).register()
        val bundle = intent.getBundleExtra("bundle")
        bundle?.let {
            bean = bundle.getParcelable("data")
        }
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
        GSYVideoManager.releaseAllVideos()
        orientationUtils.let {
            orientationUtils.releaseListener()
        }
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!vp.isIfCurrentIsFullscreen) {
                    vp.startWindowFullscreen(context, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (vp.isIfCurrentIsFullscreen) {
                    GSYVideoManager.backFromWindowFull(context)
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
        if (GSYVideoManager.backFromWindowFull(context)) {
            return
        }
        super.onBackPressed()
    }

    override fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?) {
        beans?.let {
            adapter.setList(beans)
            adapter.addFooterView(FooterUtil.getFooter(context, context.color(R.color.white)))
        }
    }

    private fun initView() {
        bean?.let {
            bean!!.id?.let {
                presenter.load(bean!!.id.toString())
                bean!!.savePath =
                    Environment.getExternalStorageDirectory().absolutePath + File.separator + KT_VIDEO + File.separator + "download_${bean!!.id}.mp4"
            }
            val blurred = bean!!.blurred
            if (blurred.isNullOrEmpty()) {
                iv_bg.setImageResource(R.color.app_black)
            } else {
                ImageUtil.showHigh(context, iv_bg, blurred)
            }
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
            val header = View.inflate(context, R.layout.item_video_related_header, null)
            playUrl = bean!!.playUrl
            val tvTitle = header.findViewById<AppCompatTextView>(R.id.tv_title)
            val tvDesc = header.findViewById<AppCompatTextView>(R.id.tv_desc)
            val tvTime = header.findViewById<AppCompatTextView>(R.id.tv_time)
            val tvFavorite = header.findViewById<AppCompatTextView>(R.id.tv_favorite)
            val tvShare = header.findViewById<AppCompatTextView>(R.id.tv_share)
            val tvReply = header.findViewById<AppCompatTextView>(R.id.tv_reply)
            val llDownload = header.findViewById<LinearLayout>(R.id.ll_download)
            tvTitle.text = bean!!.title
            tvTitle.typeface =
                Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
            tvDesc.text = bean!!.description
            tvDesc.movementMethod = ScrollingMovementMethod.getInstance()
            val category = bean!!.category
            val duration = bean!!.duration
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
            tvTime.text = time
            tvFavorite.text = bean!!.collect.toString()
            tvShare.text = bean!!.share.toString()
            tvReply.text = bean!!.reply.toString()
            llDownload.visibility =
                if (intent.getBooleanExtra("showCache", true)) View.VISIBLE else View.GONE
            llDownload.setOnClickListener {
                playUrl?.let {
                    if (dbManager.find(playUrl!!) == null) {
                        cache()
                    } else {
                        dbBean = dbManager.find(playUrl!!)
                        when (dbBean!!.downloadState) {
                            DownloadState.NORMAL.name -> {
                                cache()
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
                    dbManager.insert(bean!!)
                }
            }
            adapter.addHeaderView(header)
            adapter.setOnItemClickListener { adapter, _, position ->
                    val bean: VideoRelatedBean.Item.Data? =
                        adapter.data[position] as VideoRelatedBean.Item.Data
                    bean?.let {
                        val intent = Intent(context, VideoDetailActivity::class.java)
                        val videoBean = VideoBean(
                            bean.id,
                            bean.cover?.feed,
                            bean.title,
                            bean.description,
                            bean.duration,
                            bean.playUrl,
                            bean.category,
                            bean.cover?.blurred,
                            bean.consumption?.collectionCount,
                            bean.consumption?.shareCount,
                            bean.consumption?.replyCount,
                            System.currentTimeMillis()
                        )
                        val bundle = Bundle()
                        bundle.putParcelable("data", videoBean)
                        intent.putExtra("bundle", bundle)
                        intent.putExtra("showCache", true)
                        startActivity(intent)
                        finish()
                    }
                }
            adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        }
    }

    /**
     * 缓存
     */
    private fun cache() {
        bean?.savePath?.let {
            Aria.download(this)
                .load(playUrl!!)
                .setFilePath(bean!!.savePath!!)
                .start()
        }
    }

    private fun prepareVideo() {
        val uri = intent.getStringExtra("loac©lFile")
        if (uri != null) {
            vp.setUp(uri, false, null, null)
        } else {
            bean?.playUrl?.let {
                vp.setUp(bean!!.playUrl, false, null, null)
            }
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
            vp.startWindowFullscreen(
                context,
                true,
                true
            )//第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        }
        vp.setVideoAllCallBack(object : VideoListener() {
            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils.let {
                    orientationUtils.backToProtVideo()
                }
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                orientationUtils.isEnable = true//开始播放了才能旋转和全屏
                isPlay = true
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
        bean?.feed?.let {
            val observable: Observable<Bitmap> = Observable.create { emitter ->
                val cacheFile = GlideApp
                    .with(context)
                    .downloadOnly()
                    .load(bean!!.feed)
                    .submit()
                    .get()
                val path: String? = cacheFile.absolutePath
                val inputStream: FileInputStream? = FileInputStream(path)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                emitter.onNext(bitmap)
            }
            observable.ioMain().subscribe(object : Observer<Bitmap> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                    coverDisposable = d
                }

                override fun onNext(bitmap: Bitmap) {
                    ivCover.setImageBitmap(bitmap)
                    vp.thumbImageView = ivCover
                }

                override fun onError(e: Throwable) {

                }
            })
        }
    }

    @Download.onTaskStart
    fun onDownloadStart(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("开始>>>${bean!!.title}")
                shortToast(getString(R.string.cache_start))
                bean!!.downloadState = DownloadState.DOWNLOADING.name
                dbManager.update(bean!!)
            }
        }
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("进度>>>${task.percent}>>>${bean!!.title}")
                bean!!.downloadProgress = task.percent
                dbManager.update(bean!!)
            }
        }
    }

    @Download.onTaskFail
    fun onDownloadFail(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("失败>>>${bean!!.title}")
                shortToast(getString(R.string.cache_fail))
                bean!!.downloadState = DownloadState.ERROR.name
                bean!!.downloadProgress = 0
                dbManager.update(bean!!)
            }
        }
    }

    @Download.onTaskComplete
    fun onDownloadComplete(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("完成>>>${bean!!.title}")
                shortToast(getString(R.string.cache_complete))
                bean!!.downloadState = DownloadState.COMPLETE.name
                bean!!.downloadProgress = 100
                dbManager.update(bean!!)
            }
        }
    }

}