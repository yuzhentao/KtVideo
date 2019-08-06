package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HotAdapter
import com.yuzhentao.ktvideo.ui.fragment.DiscoverLeftFragment
import com.yuzhentao.ktvideo.ui.fragment.DiscoverRightFragment
import com.yuzhentao.ktvideo.ui.fragment.RankingFragment
import com.yuzhentao.ktvideo.util.ViewUtil
import kotlinx.android.synthetic.main.activity_discover_detail.*
import kotlin.math.abs

/**
 * 发现详情
 */
class DiscoverDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: DiscoverDetailActivity = this

    lateinit var fragments: ArrayList<Fragment>
    private var titles = mutableListOf("推荐", "广场")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_detail)
        ImmersionBar.with(activity).transparentBar().barAlpha(0.2F).init()
        initView()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            else -> {

            }
        }
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.setDisplayShowTitleEnabled(false)
        }
        ViewUtil.setMargins(tb, 0, ImmersionBar.getStatusBarHeight(activity), 0, 0)
        iv_back.setOnClickListener(this)
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

        val leftFragment = DiscoverLeftFragment()
        val leftBundle = Bundle()
        leftFragment.arguments = leftBundle

        val rightFragment = DiscoverRightFragment()
        val rightBundle = Bundle()
        rightFragment.arguments = rightBundle

        fragments = ArrayList()
        fragments.add(leftFragment)
        fragments.add(rightFragment)

        vp.adapter = HotAdapter(supportFragmentManager, fragments, titles)
        tl.setupWithViewPager(vp)
        tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.let {
                    if (tab.position < fragments.size) {
                        (fragments[tab.position] as RankingFragment).scrollToTop()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
        })
        for (i in titles.indices) {
            val tab = tl.getTabAt(i) ?: continue

            tab.setCustomView(R.layout.layout_tab)
            if (tab.customView == null) {
                continue
            }

            val tv = tab.customView!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = titles[i]
            if (i == 0) {
                tv.isSelected = true
                tab.customView!!.findViewById<View>(R.id.v_line).isSelected = true
            }
        }
    }

}