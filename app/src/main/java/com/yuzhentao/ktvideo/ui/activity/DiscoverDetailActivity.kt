package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.util.ViewUtil
import kotlinx.android.synthetic.main.activity_discover_detail.*
import kotlin.math.abs

/**
 * 发现详情
 */
class DiscoverDetailActivity : AppCompatActivity() {

    private var context: Context = this
    private var activity: DiscoverDetailActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_detail)
        ImmersionBar.with(activity).transparentBar().barAlpha(0.2F).init()
        initView()
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.setDisplayShowTitleEnabled(false)
        }
        ViewUtil.setMargins(tb, 0, ImmersionBar.getStatusBarHeight(activity), 0, 0)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            when {
                i == 0 -> {//展开
                    iv_back.setImageResource(R.drawable.ic_back_white)
                }
                abs(i) >= appBarLayout.totalScrollRange -> {//收缩
                    iv_back.setImageResource(R.drawable.ic_back_black)
                }
                else -> {//默认
                    iv_back.setImageResource(R.drawable.ic_back_white)
                }
            }
        })
    }

}