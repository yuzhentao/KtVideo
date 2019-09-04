package com.yuzhentao.ktvideo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class RankingAdapter(fm: FragmentManager?, var beans: MutableList<Fragment>, var titles: MutableList<String>) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return beans[position]
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}