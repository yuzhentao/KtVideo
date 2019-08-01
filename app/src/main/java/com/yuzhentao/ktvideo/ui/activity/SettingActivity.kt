package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import kotlinx.android.synthetic.main.activity_cache.*

class SettingActivity : AppCompatActivity() {

    var context: Context = this
    var activity: SettingActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        ImmersionBar.with(activity).transparentBar().barAlpha(0.3F).fitsSystemWindows(true).init()
        initView()
        initData()
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.title = getString(R.string.mine_setting)
            bar.setDisplayHomeAsUpEnabled(true)
            tb.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initData() {

    }

}