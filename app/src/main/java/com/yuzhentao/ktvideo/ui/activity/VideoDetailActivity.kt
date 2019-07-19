package com.yuzhentao.ktvideo.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.*
import kotlinx.android.synthetic.main.activity_video_detail.*
import java.io.FileInputStream

class VideoDetailActivity : AppCompatActivity() {

    companion object {

        var MSG_IMAGE_LOADED = 1000

    }

    var context: Context = this
    var activity: VideoDetailActivity = this

    private lateinit var bean: VideoBean

    lateinit var ivCover: ImageView//封面
    var isPlay: Boolean = false
    var isPause: Boolean = false
    lateinit var orientationUtils: OrientationUtils//处理屏幕旋转的逻辑
    var handler: Handler =
            @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                    when (msg?.what) {
                        MSG_IMAGE_LOADED -> {
                            vp.setThumbImageView(ivCover)
                        }
                    }
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
        bean = intent.getParcelableExtra("data")
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
            val url = bean.playUrl?.let {
                SPUtils.getInstance(context, "downloads").getString(it)//是否缓存当前视频
            }
            if (url == "") {
                var count = SPUtils.getInstance(context, "downloads").getInt("count")
                count = if (count != -1) {
                    count.inc()
                } else {
                    1
                }
                SPUtils.getInstance(context, "downloads").put("count", count)//缓存的视频对象数量
                ObjectSaveUtils.saveObject(context, "download$count", bean)//保存视频对象，缓存中会使用到
                cache(bean.playUrl, count)
            } else {
                showToast("该视频已经缓存过了")
            }
        }
    }

    /**
     * 缓存
     * http://cdn.sinaweibo.com.cn//uploadFiles/uploadFile/2019010803200685761.webp
     */
    @SuppressLint("CheckResult")
    private fun cache(playUrl: String?, count: Int) {

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
        ImageViewAsyncTask(handler, activity, ivCover).execute(bean.feed)
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

    private class ImageViewAsyncTask(handler: Handler, activity: VideoDetailActivity, private val iv: ImageView) : AsyncTask<String, Void, String>() {

        private var handler = handler
        private var path: String? = null
        private var inputStream: FileInputStream? = null
        private var activity = activity

        override fun doInBackground(vararg params: String?): String {
            val future = GlideApp.with(activity)
                    .load(params[0])
                    .downloadOnly(100, 100)
            val cacheFile = future.get()
            path = cacheFile.absolutePath
            return path!!
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            inputStream = FileInputStream(result)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            iv.setImageBitmap(bitmap)
            val message = handler.obtainMessage()
            message.what = MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

}