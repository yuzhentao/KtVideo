package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 观看记录
 */
class WatchActivity : AppCompatActivity() {

    var context: Context = this
    var activity: WatchActivity = this

    var beans = ArrayList<VideoBean>()

    private lateinit var dbManager: VideoDbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        dbManager = VideoDbManager()
        ImmersionBar.with(activity).transparentBar().barAlpha(0.3F).fitsSystemWindows(true).init()
        initView()
        initData()
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.title = getString(R.string.mine_watch)
            bar.setDisplayHomeAsUpEnabled(true)
            tb.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initData() {

    }

}