package com.yzt.ranking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.yzt.bean.RankingBean
import com.yzt.common.base.BaseFragment
import com.yzt.ranking.R
import com.yzt.ranking.adapter.RankingPagerAdapter
import com.yzt.ranking.databinding.FragmentRankingBinding
import com.yzt.ranking.mvp.contract.RankingContract
import com.yzt.ranking.mvp.presenter.RankingPresenter

/**
 * 排行
 *
 * @author yzt 2020/12/31
 */
class RankingFragment : BaseFragment(), RankingContract.View {

    private var binding: FragmentRankingBinding? = null

    private lateinit var titles: MutableList<String>
    private lateinit var strategies: MutableList<String>
    private lateinit var fragments: MutableList<androidx.fragment.app.Fragment>

    private val presenter: RankingPresenter by lazy {
        RankingPresenter(context, this)
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
        presenter.load()
    }

    override fun initData() {

    }

    override fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?) {
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

            binding!!.vp.adapter = RankingPagerAdapter(
                requireFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments,
                titles
            )
            binding!!.tl.setupWithViewPager(binding!!.vp)
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

}