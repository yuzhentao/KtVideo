package com.yuzhentao.ktvideo.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.ImageUtil
import kotlinx.android.synthetic.main.activity_video_detail.*

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
    }

    private fun prepareVideo() {

    }

}