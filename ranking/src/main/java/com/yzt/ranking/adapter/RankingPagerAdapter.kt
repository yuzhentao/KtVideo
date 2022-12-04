package com.yzt.ranking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 排行-分页
 *
 * @author yzt 2021/2/9
 */
//class RankingPagerAdapter(
//    fm: FragmentManager,
//    behavior: Int,
//    var beans: MutableList<Fragment>,
//    var titles: MutableList<String>
//) : FragmentPagerAdapter(fm, behavior) {
//
//    override fun getItem(position: Int): Fragment {
//        return beans[position]
//    }
//
//    override fun getCount(): Int {
//        return titles.size
//    }
//
//    override fun getPageTitle(position: Int): CharSequence? {
//        return titles[position]
//    }
//
//}

class RankingPagerAdapter(
    fa: FragmentActivity,
    var beans: MutableList<Fragment>
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return beans.size
    }

    override fun createFragment(position: Int): Fragment {
        return beans[position]
    }

}