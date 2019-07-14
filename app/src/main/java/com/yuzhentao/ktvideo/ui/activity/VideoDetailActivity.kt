package com.yuzhentao.ktvideo.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yuzhentao.ktvideo.R

class VideoDetailActivity : AppCompatActivity() {

    companion object {

        var MSG_IMAGE_LOADED = 101

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }

}