package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yzt.common.bean.VideoBean
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.extension.ioMain
import com.yzt.common.extension.shortToast
import com.yzt.common.util.DownloadState
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.VideoRelatedAdapter
import com.yzt.ktvideo.bean.VideoRelatedBean
import com.yzt.ktvideo.mvp.contract.VideoRelatedContract
import com.yzt.ktvideo.mvp.presenter.VideoRelatedPresenter
import com.yzt.ktvideo.util.FooterUtil
import com.yzt.ktvideo.util.GlideApp
import com.yzt.ktvideo.util.ImageUtil
import com.yzt.ktvideo.util.VideoListener
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
    private var downloadId: Long = 0L
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
            bean = it.getParcelable("data")
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
        coverDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
        GSYVideoManager.releaseAllVideos()
        orientationUtils.releaseListener()
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
                orientationUtils.isEnable = true
            }
        }
    }

    override fun onBackPressed() {
        orientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(context)) {
            return
        }
        super.onBackPressed()
    }

    override fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?) {
        beans?.let {
            adapter.setList(it)
            adapter.addFooterView(FooterUtil.getFooter(context, color(R.color.white)))
        }
    }

    private fun initView() {
        bean?.let {
            it.id?.let { itt ->
                presenter.load(itt.toString())
                it.savePath =
                    getExternalFilesDir(null)!!.absolutePath + File.separator + "download_${itt}.mp4"
            }
            val blurred = it.blurred
            if (blurred.isNullOrEmpty()) {
                iv_bg.setImageResource(R.color.app_black)
            } else {
                ImageUtil.showHigh(context, iv_bg, blurred)
            }
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter
            val header = View.inflate(context, R.layout.item_video_related_header, null)
            playUrl = it.playUrl
            val tvTitle = header.findViewById<AppCompatTextView>(R.id.tv_title)
            val tvDesc = header.findViewById<AppCompatTextView>(R.id.tv_desc)
            val tvTime = header.findViewById<AppCompatTextView>(R.id.tv_time)
            val tvFavorite = header.findViewById<AppCompatTextView>(R.id.tv_favorite)
            val tvShare = header.findViewById<AppCompatTextView>(R.id.tv_share)
            val tvReply = header.findViewById<AppCompatTextView>(R.id.tv_reply)
            val llDownload = header.findViewById<LinearLayout>(R.id.ll_download)
            tvTitle.text = it.title
            tvTitle.typeface =
                Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
            tvDesc.text = it.description
            tvDesc.movementMethod = ScrollingMovementMethod.getInstance()
            val category = it.category
            val duration = it.duration
            val minute = duration?.div(60)
            val second = minute?.times(60)?.let { itt ->
                duration.minus(itt)
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
            tvFavorite.text = it.collect.toString()
            tvShare.text = it.share.toString()
            tvReply.text = it.reply.toString()
            llDownload.visibility =
                if (intent.getBooleanExtra("showCache", true)) View.VISIBLE else View.GONE
            llDownload.setOnClickListener {
                playUrl?.let { itt ->
                    if (dbManager.find(itt) == null) {
                        cache()
                    } else {
                        dbBean = dbManager.find(itt)
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
            playUrl?.let { itt ->
                if (dbManager.find(itt) == null) {
                    dbManager.insert(it)
                }
            }
            adapter.addHeaderView(header)
            adapter.setOnItemClickListener { adapter, _, position ->
                val bean: VideoRelatedBean.Item.Data? =
                    adapter.data[position] as VideoRelatedBean.Item.Data
                bean?.let { itt ->
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    val videoBean = VideoBean(
                        itt.id,
                        itt.cover?.feed,
                        itt.title,
                        itt.description,
                        itt.duration,
                        itt.playUrl,
                        itt.category,
                        itt.cover?.blurred,
                        itt.consumption?.collectionCount,
                        itt.consumption?.shareCount,
                        itt.consumption?.replyCount,
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
            downloadId = Aria
                .download(this)
                .load(playUrl!!)
                .setFilePath(it)
                .create()
        }
    }

    private fun prepareVideo() {
        val uri = intent.getStringExtra("loac©lFile")
        if (uri != null) {
            vp.setUp(uri, false, null, null)
        } else {
            bean?.playUrl?.let {
                vp.setUp(it, false, null, null)
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
                orientationUtils.backToProtVideo()
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
                    .load(it)
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
                Timber.tag("缓存").e("开始>>>${it.title}")
                shortToast(getString(R.string.cache_start))
                it.downloadId = downloadId
                it.downloadState = DownloadState.DOWNLOADING.name
                dbManager.update(it)
            }
        }
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("进度>>>${task.percent}>>>${it.title}")
                it.downloadProgress = task.percent
                dbManager.update(it)
            }
        }
    }

    @Download.onTaskFail
    fun onDownloadFail(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("失败>>>${it.title}")
                shortToast(getString(R.string.cache_fail))
                it.downloadState = DownloadState.ERROR.name
                it.downloadProgress = 0
                dbManager.update(it)
            }
        }
    }

    @Download.onTaskComplete
    fun onDownloadComplete(task: DownloadTask) {
        if (task.key == playUrl) {
            bean?.let {
                Timber.tag("缓存").e("完成>>>${it.title}")
                shortToast(getString(R.string.cache_complete))
                it.downloadState = DownloadState.COMPLETE.name
                it.downloadProgress = 100
                dbManager.update(it)
            }
        }
    }

}