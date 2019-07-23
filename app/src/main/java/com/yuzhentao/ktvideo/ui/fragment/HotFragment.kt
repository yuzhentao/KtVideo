package com.yuzhentao.ktvideo.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HotAdapter
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment : BaseFragment() {

    lateinit var fragments: ArrayList<Fragment>
    private var titles = mutableListOf("周排行", "月排行", "总排行")
    var strategy = arrayOf("weekly", "monthly", "historical")

    override fun getLayoutResources(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        val weekFragment = RankingFragment()
        val weekBundle = Bundle()
        weekBundle.putString("strategy", strategy[0])
        weekFragment.arguments = weekBundle

        val monthFragment = RankingFragment()
        val monthBundle = Bundle()
        monthBundle.putString("strategy", strategy[0])
        monthFragment.arguments = monthBundle

        val allFragment = RankingFragment()
        val allBundle = Bundle()
        allBundle.putString("strategy", strategy[0])
        allFragment.arguments = allBundle

        fragments = ArrayList()
        fragments.add(weekFragment)
        fragments.add(monthFragment)
        fragments.add(allFragment)

        vp.adapter = HotAdapter(fragmentManager, fragments, titles)
        tl.setupWithViewPager(vp)
        tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

}