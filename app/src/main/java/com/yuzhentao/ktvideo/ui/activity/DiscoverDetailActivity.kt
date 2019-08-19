package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HotAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailPresenter
import com.yuzhentao.ktvideo.ui.fragment.DiscoverLeftFragment
import com.yuzhentao.ktvideo.ui.fragment.DiscoverRightFragment
import com.yuzhentao.ktvideo.ui.fragment.RankingFragment
import com.yuzhentao.ktvideo.util.ImageUtil
import kotlinx.android.synthetic.main.activity_discover_detail.*
import kotlin.math.abs

/**
 * 发现详情
 */
class DiscoverDetailActivity : AppCompatActivity(), View.OnClickListener, DiscoverDetailContract.View {

    private var context: Context = this
    private var activity: DiscoverDetailActivity = this

    var presenter: DiscoverDetailPresenter? = null

    lateinit var fragments: ArrayList<Fragment>
    private var titles = mutableListOf("推荐", "广场")

    private var id: String? = null
    var category: String? = null

    private var isChange: Boolean? = false
    private var isLeft: Boolean? = true

    var isFull: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_discover_detail)
        initView()
        initData()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        GSYVideoManager.releaseAllVideos()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(context)) {
            return
        }
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        newConfig?.let {
            isFull = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_back, R.id.iv_top -> {
                onBackPressed()
            }
            R.id.tv_follow -> {

            }
        }
    }

    override fun setData(bean: DiscoverDetailBean?) {
        bean?.let {
            category = bean.tagInfo.name
            tv_top.text = category
            tv_name.text = category
            tv_desc.text = bean.tagInfo.description
            var count = ""
            if (bean.tagInfo.tagFollowCount.toString().isNotEmpty() && getString(R.string.discover_join, bean.tagInfo.lookCount.toString()).isEmpty()) {
                count = getString(R.string.discover_follow, bean.tagInfo.tagFollowCount.toString())
            } else if (bean.tagInfo.tagFollowCount.toString().isEmpty() && getString(R.string.discover_join, bean.tagInfo.lookCount.toString()).isNotEmpty()) {
                count = getString(R.string.discover_join, bean.tagInfo.lookCount.toString())
            } else if (bean.tagInfo.tagFollowCount.toString().isNotEmpty() && getString(R.string.discover_join, bean.tagInfo.lookCount.toString()).isNotEmpty()) {
                count = getString(R.string.discover_follow, bean.tagInfo.tagFollowCount.toString()) + " | " + getString(R.string.discover_join, bean.tagInfo.lookCount.toString())
            } else {
                tv_count.visibility = View.GONE
            }
            tv_count.text = count
            ImageUtil.show(context, iv, bean.tagInfo.headerImage)
        }
    }

    private fun initView() {
        id = intent.getStringExtra("id");
        id?.let {
            presenter = DiscoverDetailPresenter(context, this)
            presenter?.load(id!!)
        }

        iv_back.setOnClickListener(this)
        iv_top.setOnClickListener(this)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            when {
                i == 0 -> {//展开
                    tb.alpha = 0F
                    tv_top.visibility = View.GONE
                    iv_back.visibility = View.VISIBLE
                    isChange = false
                }
                abs(i) > 0 -> {//移动中
                    tb.alpha = abs(i) / appBarLayout.totalScrollRange.toFloat()
                    if (!isChange!!) {
                        tv_top.visibility = View.VISIBLE
                        iv_back.visibility = View.GONE
                    }
                    isChange = true
                }
                abs(i) >= appBarLayout.totalScrollRange -> {//收缩
                    tb.alpha = 1F
                }
            }
        })

        tv_name.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_follow.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_follow.setOnClickListener(this)

        val leftFragment = DiscoverLeftFragment()
        val leftBundle = Bundle()
        leftBundle.putString("id", id)
        leftFragment.arguments = leftBundle

        val rightFragment = DiscoverRightFragment()
        val rightBundle = Bundle()
        rightBundle.putString("id", id)
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

    private fun initData() {

    }

}