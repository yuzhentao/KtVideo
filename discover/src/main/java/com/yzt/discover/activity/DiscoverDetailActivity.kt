package com.yzt.discover.activity

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentPagerAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ktx.immersionBar
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.yzt.bean.DiscoverDetailBean
import com.yzt.common.base.BaseActivity
import com.yzt.common.extension.color
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.ImageUtil
import com.yzt.discover.R
import com.yzt.discover.adapter.DiscoverPagerAdapter
import com.yzt.discover.databinding.ActivityDiscoverDetailBinding
import com.yzt.discover.fragment.DiscoverLeftFragment
import com.yzt.discover.fragment.DiscoverRightFragment
import com.yzt.discover.mvp.contract.DiscoverDetailContract
import com.yzt.discover.mvp.presenter.DiscoverDetailPresenter
import kotlin.math.abs

/**
 * 发现详情
 */
@Route(path = Constant.PATH_DISCOVER_DETAIL)
class DiscoverDetailActivity : BaseActivity(), View.OnClickListener,
    DiscoverDetailContract.View {

    private var binding: ActivityDiscoverDetailBinding? = null

    private val presenter: DiscoverDetailPresenter by lazy {
        DiscoverDetailPresenter(context!!, this)
    }

    private lateinit var fragments: MutableList<androidx.fragment.app.Fragment>
    private lateinit var titles: MutableList<String>

    private var id: String? = null
    var category: String? = null
    private var isChange: Boolean? = false
    var isFull: Boolean = false

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityDiscoverDetailBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        id = intent.getStringExtra("id")
        id?.let {
            presenter.load(id!!)
        }

        binding!!.ivBack.setOnClickListener(this)
        binding!!.ivTop.setOnClickListener(this)
        binding!!.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
            when {
                i == 0 -> {//展开
                    binding!!.tb.alpha = 0F
                    binding!!.tvTop.visibility = View.GONE
                    binding!!.ivBack.visibility = View.VISIBLE
                    isChange = false
                }
                abs(i) > 0 -> {//移动中
                    binding!!.tb.alpha = abs(i) / appBarLayout.totalScrollRange.toFloat()
                    if (!isChange!!) {
                        binding!!.tvTop.visibility = View.VISIBLE
                        binding!!.ivBack.visibility = View.GONE
                    }
                    isChange = true
                }
                abs(i) >= binding!!.appBarLayout.totalScrollRange -> {//收缩
                    binding!!.tb.alpha = 1F
                }
            }
        })

        binding!!.tvName.typeface =
            Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        binding!!.tvFollow.typeface =
            Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        binding!!.tvFollow.setOnClickListener(this)

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

    override fun initData(savedInstanceState: Bundle?) {

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
            binding!!.tvTop.text = category
            binding!!.tvName.text = category
            binding!!.tvDesc.text = it.description
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
                binding!!.tvCount.visibility = View.GONE
            }
            binding!!.tvCount.text = count
            ImageUtil.show(context!!, binding!!.iv, it.headerImage)
            binding!!.iv.setColorFilter(context!!.color(R.color.black_25))

            titles = mutableListOf()
            bean.tabInfo.tabList.forEach { itt ->
                itt.name?.let { name ->
                    titles.add(name)
                }
            }
            binding!!.vp.adapter = DiscoverPagerAdapter(
                supportFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments,
                titles
            )
            binding!!.tl.setupWithViewPager(binding!!.vp)
            binding!!.tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                val tab = binding!!.tl.getTabAt(i) ?: continue

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

}