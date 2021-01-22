package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.yzt.common.util.ClickUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.RankingAdapter
import com.yzt.ktvideo.bean.DiscoverDetailBean
import com.yzt.common.extension.color
import com.yzt.ktvideo.mvp.contract.DiscoverDetailContract
import com.yzt.ktvideo.mvp.presenter.DiscoverDetailPresenter
import com.yzt.ktvideo.ui.fragment.DiscoverLeftFragment
import com.yzt.ktvideo.ui.fragment.DiscoverRightFragment
import com.yzt.common.util.ImageUtil
import kotlinx.android.synthetic.main.activity_discover_detail.*
import kotlin.math.abs

/**
 * 发现详情
 */
class DiscoverDetailActivity : AppCompatActivity(), View.OnClickListener,
    DiscoverDetailContract.View {

    private var context: Context = this

    private val presenter: DiscoverDetailPresenter by lazy {
        DiscoverDetailPresenter(context, this)
    }

    private lateinit var fragments: MutableList<androidx.fragment.app.Fragment>
    private lateinit var titles: MutableList<String>

    private var id: String? = null
    var category: String? = null
    private var isChange: Boolean? = false
    var isFull: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        isFull = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_back, R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(
                        R.id.iv_back,
                        1000
                    ) || !ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)
                ) {
                    onBackPressed()
                }
            }
            R.id.tv_follow -> {

            }
        }
    }

    override fun setData(bean: DiscoverDetailBean?) {
        bean?.tagInfo?.let {
            category = it.name
            tv_top.text = category
            tv_name.text = category
            tv_desc.text = it.description
            var count = ""
            if (it.tagFollowCount.toString().isNotEmpty() && getString(
                    R.string.discover_join,
                    it.lookCount.toString()
                ).isEmpty()
            ) {
                count = getString(R.string.discover_follow, it.tagFollowCount.toString())
            } else if (it.tagFollowCount.toString().isEmpty() && getString(
                    R.string.discover_join,
                    it.lookCount.toString()
                ).isNotEmpty()
            ) {
                count = getString(R.string.discover_join, it.lookCount.toString())
            } else if (it.tagFollowCount.toString().isNotEmpty() && getString(
                    R.string.discover_join,
                    it.lookCount.toString()
                ).isNotEmpty()
            ) {
                count = getString(
                    R.string.discover_follow,
                    it.tagFollowCount.toString()
                ) + " | " + getString(R.string.discover_join, it.lookCount.toString())
            } else {
                tv_count.visibility = View.GONE
            }
            tv_count.text = count
            ImageUtil.show(context, iv, it.headerImage)
            iv.setColorFilter(context.color(R.color.black_25))

            titles = mutableListOf()
            bean.tabInfo.tabList.forEach { itt ->
                itt.name?.let { name ->
                    titles.add(name)
                }
            }
            vp.adapter = RankingAdapter(
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments,
                titles
            )
            tl.setupWithViewPager(vp)
            tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tab?.let { itt ->
                        when (itt.position) {
                            0 -> {
                                (fragments[0] as DiscoverLeftFragment).scrollToTop()
                            }
                            1 -> {
                                (fragments[1] as DiscoverRightFragment).scrollToTop()
                            }
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

    private fun initView() {
        id = intent.getStringExtra("id");
        id?.let {
            presenter.load(id!!)
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

        tv_name.typeface =
            Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_follow.typeface =
            Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_follow.setOnClickListener(this)

        val leftFragment = DiscoverLeftFragment()
        val leftBundle = Bundle()
        leftBundle.putString("id", id)
        leftFragment.arguments = leftBundle

        val rightFragment = DiscoverRightFragment()
        val rightBundle = Bundle()
        rightBundle.putString("id", id)
        rightFragment.arguments = rightBundle

        fragments = mutableListOf()
        fragments.add(leftFragment)
        fragments.add(rightFragment)
    }

    private fun initData() {

    }

}