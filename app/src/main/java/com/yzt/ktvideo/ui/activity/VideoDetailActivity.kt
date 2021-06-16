package com.yzt.ktvideo.ui.activity

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
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yzt.bean.DownloadState
import com.yzt.bean.VideoBean
import com.yzt.bean.VideoRelatedBean
import com.yzt.common.base.BaseActivity
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.extension.ioMain
import com.yzt.common.extension.shortToast
import com.yzt.common.key.Constant
import com.yzt.common.util.FooterUtil
import com.yzt.common.util.ImageUtil
import com.yzt.common.util.VideoListener
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.VideoRelatedAdapter
import com.yzt.ktvideo.databinding.ActivityVideoDetailBinding
import com.yzt.ktvideo.mvp.contract.VideoRelatedContract
import com.yzt.ktvideo.mvp.presenter.VideoRelatedPresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber
import java.io.File
import java.io.FileInputStream

/**
 * 视频详情
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_VIDEO_DETAIL)
class VideoDetailActivity : BaseActivity(), VideoRelatedContract.View {

    private var binding: ActivityVideoDetailBinding? = null

    @Autowired
    @JvmField
    var bean: VideoBean? = null

    @Autowired
    @JvmField
    var showCache: Boolean = false

    @Autowired
    @JvmField
    var autoPlay: Boolean = false

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

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.black)
            fitsSystemWindows(true)
        }
        ARouter.getInstance().inject(this)
        Aria.download(this).register()
    }

    override fun initView(savedInstanceState: Bundle?) {
        bean?.let {
            it.id?.let { itt ->
                presenter.load(itt.toString())
                it.savePath =
                    getExternalFilesDir(null)!!.absolutePath + File.separator + "download_${itt}.mp4"
            }
            val blurred = it.blurred
            if (blurred.isNullOrEmpty()) {
                binding!!.ivBg.setImageResource(R.color.app_black)
            } else {
                ImageUtil.showHigh(context!!, binding!!.ivBg, blurred)
            }
            binding!!.rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding!!.rv.adapter = adapter
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
            val realMinute: String = if (minute!! < 10) {
                "0$minute"
            } else {
                minute.toString()
            }
            val realSecond: String = if (second!! < 10) {
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
                if (showCache) View.VISIBLE else View.GONE
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
            llDownload.setOnLongClickListener { true }
            playUrl?.let { itt ->
                if (dbManager.find(itt) == null) {
                    dbManager.insert(it)
                }
            }
            adapter.addHeaderView(header)
            adapter.setOnItemClickListener { adapter, _, position ->
                val bean: VideoRelatedBean.Item.Data? =
                    adapter.data[position] as VideoRelatedBean.Item.Data?
                bean?.let { itt ->
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
                    ARouter
                        .getInstance()
                        .build(Constant.PATH_VIDEO_DETAIL)
                        .withParcelable("bean", videoBean)
                        .withBoolean("showCache", true)
                        .navigation()
                    finish()
                }
            }
            adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
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
                if (!binding!!.vp.isIfCurrentIsFullscreen) {
                    binding!!.vp.startWindowFullscreen(context, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (binding!!.vp.isIfCurrentIsFullscreen) {
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
            adapter.addFooterView(FooterUtil.getFooter(context!!, color(R.color.white)))
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
            binding!!.vp.setUp(uri, false, null, null)
        } else {
            bean?.playUrl?.let {
                binding!!.vp.setUp(it, false, null, null)
            }
        }
        ivCover = ImageView(context)
        ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
        setCover()
        binding!!.vp.titleTextView.visibility = View.GONE
        binding!!.vp.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(activity, binding!!.vp)
        binding!!.vp.setIsTouchWiget(true)
        binding!!.vp.isRotateViewAuto = false
        binding!!.vp.isLockLand = false
        binding!!.vp.isShowFullAnimation = false
        binding!!.vp.isNeedLockFull = true
        binding!!.vp.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()//直接横屏
            binding!!.vp.startWindowFullscreen(
                context,
                true,
                true
            )//第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        }
        binding!!.vp.setVideoAllCallBack(object : VideoListener() {
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
        binding!!.vp.setLockClickListener { _, lock ->
            orientationUtils.isEnable = !lock//配合下方的onConfigurationChanged
        }
        binding!!.vp.backButton.setOnClickListener {
            onBackPressed()
        }
        if (autoPlay) {
            binding!!.vp.startPlayLogic()
        }
    }

    private fun setCover() {
        bean?.feed?.let {
            val observable: Observable<Bitmap> = Observable.create { emitter ->
                val cacheFile = ImageUtil
                    .get(context!!)
                    .downloadOnly()
                    .load(it)
                    .submit()
                    .get()
                val path: String? = cacheFile.absolutePath
                val inputStream = FileInputStream(path)
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
                    binding!!.vp.thumbImageView = ivCover
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