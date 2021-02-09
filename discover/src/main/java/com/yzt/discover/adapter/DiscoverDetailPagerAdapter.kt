package com.yzt.discover.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 发现详情-分页
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailPagerAdapter(
    fm: FragmentManager,
    behavior: Int,
    var beans: MutableList<Fragment>,
    var titles: MutableList<String>
) : FragmentPagerAdapter(fm, behavior) {

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