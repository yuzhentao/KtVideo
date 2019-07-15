package com.yuzhentao.ktvideo.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ObjectSaveUtils
import com.yuzhentao.ktvideo.util.SPUtils
import com.yuzhentao.ktvideo.util.showToast
import kotlinx.android.synthetic.main.activity_video_detail.*
import zlc.season.rxdownload2.RxDownload

class VideoDetailActivity : AppCompatActivity() {

    companion object {

        var MSG_IMAGE_LOADED = 101

    }

    var context: Context = this
    lateinit var iv: ImageView
    lateinit var bean: VideoBean
    var isPlay: Boolean = false
    var isPause: Boolean = false
    lateinit var orientationUtils: OrientationUtils
    var handler: Handler =
            @SuppressLint("HandlerLeak")
            object : Handler() {
                override fun handleMessage(msg: Message?) {
                    super.handleMessage(msg)
                    when (msg?.what) {
                        MSG_IMAGE_LOADED -> {
                            vp.setThumbImageView(iv)
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

    private fun initView() {
        val bgUrl = bean.blurred
        bgUrl?.let {
            ImageUtil.displayHigh(context, iv_bg, bgUrl)
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
                SPUtils.getInstance(context, "downloads").getString(it)
            }
            if (url == "") {
                var count = SPUtils.getInstance(context, "downloads").getInt("count")
                count = if (count != -1) {
                    count.inc()
                } else {
                    1
                }
                SPUtils.getInstance(context, "downloads").put("count", count)
                ObjectSaveUtils.saveObject(context, "download$count", bean)
                addMission(bean.playUrl, count)
            } else {
                showToast("该视频已经缓存过了")
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun addMission(playUrl: String?, count: Int) {
        RxDownload
                .getInstance(context)
                .serviceDownload(playUrl, "download$count")
                .subscribe({
                    showToast("开始下载")
                    SPUtils.getInstance(context, "downloads").put(bean.playUrl.toString(), bean.playUrl.toString())
                    SPUtils.getInstance(context, "download_state").put(playUrl.toString(), true)
                }, {
                    showToast("添加任务失败")
                })
    }

    private fun prepareVideo() {
        val uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            vp.setUp(uri, false, null, null)
        } else {
            vp.setUp(bean.playUrl, false, null, null)
        }
        //封面
        iv = ImageView(context)
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        //todo
        vp.titleTextView.visibility = View.GONE
        vp.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, vp)
        vp.setIsTouchWiget(true)
        vp.isRotateViewAuto = false
        vp.isLockLand = false
        vp.isShowFullAnimation = false
        vp.isNeedLockFull = true
        vp.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()//直接横屏
            vp.startWindowFullscreen(context, true, true)//第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
        }
        vp.setStandardVideoAllCallBack(object )
    }

}