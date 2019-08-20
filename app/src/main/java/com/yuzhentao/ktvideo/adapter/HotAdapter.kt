package com.yuzhentao.ktvideo.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class HotAdapter(fm: FragmentManager?, var beans: MutableList<Fragment>, var titles: MutableList<String>) : FragmentPagerAdapter(fm) {

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