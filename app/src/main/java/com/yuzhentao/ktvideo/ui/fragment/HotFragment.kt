package com.yuzhentao.ktvideo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.yuzhentao.ktvideo.R

class HotFragment : BaseFragment() {

    private var tabs = mutableListOf("周排行", "月排行", "总排行")
    lateinit var fragments: ArrayList<Fragment>
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
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

}