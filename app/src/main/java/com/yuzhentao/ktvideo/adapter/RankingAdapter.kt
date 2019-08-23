package com.yuzhentao.ktvideo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RankingAdapter(fm: androidx.fragment.app.FragmentManager?, var beans: MutableList<androidx.fragment.app.Fragment>, var titles: MutableList<String>) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return beans[position]
    }

    override fun getCount(): Int {
        return titles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}