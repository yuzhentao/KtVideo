package com.yuzhentao.ktvideo.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingAdapter
import com.yuzhentao.ktvideo.bean.RankingBean
import com.yuzhentao.ktvideo.mvp.contract.RankingContract
import com.yuzhentao.ktvideo.mvp.presenter.RankingPresenter
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * 热门
 */
class RankingFragment : BaseFragment(), RankingContract.View {

    private lateinit var titles: MutableList<String>
    private lateinit var strategies: MutableList<String>
    private lateinit var fragments: MutableList<Fragment>

    private var presenter: RankingPresenter? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        presenter = RankingPresenter(context, this)
        presenter?.load()
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?) {
        beans?.let {
            titles = mutableListOf()
            strategies = mutableListOf()
            fragments = mutableListOf()

            for (item in beans) {
                item.name?.let {
                    titles.add(item.name)
                }
                item.apiUrl?.let {
                    strategies.add(item.apiUrl.substring(item.apiUrl.lastIndexOf("=") + 1))
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

            vp.adapter = RankingAdapter(fragmentManager, fragments, titles)
            tl.setupWithViewPager(vp)
            tl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tab?.let {
                        if (tab.position < fragments.size) {
                            (fragments[tab.position] as RankingSubFragment).scrollToTop()
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabSelected(tab: TabLayout.Tab?) {

                }
            })
            for (i in titles.indices) {
                val tab = tl.getTabAt(i) ?: continue

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