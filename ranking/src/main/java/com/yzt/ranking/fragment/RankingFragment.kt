package com.yzt.ranking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.gyf.immersionbar.ImmersionBar
import com.yzt.bean.RankingBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.util.DimenUtil
import com.yzt.common.util.ViewUtil
import com.yzt.ranking.R
import com.yzt.ranking.adapter.RankingPagerAdapter
import com.yzt.ranking.databinding.FragmentRankingBinding
import com.yzt.ranking.viewmodel.RankingViewModel
import com.yzt.ranking.viewmodel.RankingViewModelFactory

/**
 * 排行
 *
 * @author yzt 2020/12/31
 */
class RankingFragment : BaseFragment() {

    private var binding: FragmentRankingBinding? = null

    private lateinit var titles: MutableList<String>
    private lateinit var strategies: MutableList<String>
    private lateinit var fragments: MutableList<androidx.fragment.app.Fragment>

    private val viewModel: RankingViewModel by lazy {
        ViewModelProvider(this, RankingViewModelFactory())[RankingViewModel::class.java]
    }

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentRankingBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        viewModel.load(context)
    }

    override fun initData() {
        viewModel.liveData.observe(
            this,
            Observer<MutableList<RankingBean.TabInfo.Tab>> { beans ->
                beans?.let {
                    titles = mutableListOf()
                    strategies = mutableListOf()
                    fragments = mutableListOf()
                    for (item in it) {
                        item.name?.let { itt ->
                            titles.add(itt)
                        }
                        item.apiUrl?.let { itt ->
                            strategies.add(itt.substring(itt.lastIndexOf("=") + 1))
                        }
                    }
                    val weekFragment = RankingSubFragment()
                    val weekBundle = Bundle()
                    weekBundle.putString("strategy", strategies[0])
                    weekFragment.arguments = weekBundle
                    val monthFragment = RankingSubFragment()
                    val monthBundle = Bundle()
                    monthBundle.putString("strategy", strategies[1])
                    monthFragment.arguments = monthBundle
                    val allFragment = RankingSubFragment()
                    val allBundle = Bundle()
                    allBundle.putString("strategy", strategies[2])
                    allFragment.arguments = allBundle
                    fragments.add(weekFragment)
                    fragments.add(monthFragment)
                    fragments.add(allFragment)

                    ViewUtil.setPaddings(
                        binding!!.container,
                        0,
                        ImmersionBar.getStatusBarHeight(this) + DimenUtil.dp2px(
                            requireContext(),
                            48
                        ),
                        0,
                        0,
                    )

                    //绑定TabLayout和ViewPager
//                    binding!!.vp.adapter = RankingPagerAdapter(
//                        requireFragmentManager(),
//                        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
//                        fragments,
//                        titles
//                    )
//                    binding!!.tl.setupWithViewPager(binding!!.vp)

                    //绑定TabLayout和ViewPager2
                    binding!!.vp.adapter = RankingPagerAdapter(requireActivity(), fragments)
                    TabLayoutMediator(
                        binding!!.tl,
                        binding!!.vp,
                        true,
                        true,
                        fun(tab: TabLayout.Tab, position: Int) {
                            tab.text = titles[position]
                        }
                    ).attach()

                    binding!!.tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                        override fun onTabReselected(tab: TabLayout.Tab?) {
                            tab?.let { itt ->
                                if (itt.position < fragments.size) {
                                    (fragments[itt.position] as RankingSubFragment).scrollToTop()
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
        )
    }

}