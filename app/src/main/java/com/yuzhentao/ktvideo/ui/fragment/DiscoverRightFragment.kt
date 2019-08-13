package com.yuzhentao.ktvideo.ui.fragment

import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverDetailRightAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailRightPresenter
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
import com.yuzhentao.ktvideo.util.ScrollCalculatorHelper
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 发现详情-广场
 */
class DiscoverRightFragment : BaseFragment(), DiscoverDetailRightContract.View {

    lateinit var activity: DiscoverDetailActivity

    private var presenter: DiscoverDetailRightPresenter? = null

    lateinit var adapter: DiscoverDetailRightAdapter
    lateinit var scrollCalculatorHelper: ScrollCalculatorHelper

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover_right
    }

    override fun initView() {
        arguments?.let {
            arguments!!.getString("id")?.let {
                presenter = DiscoverDetailRightPresenter(context!!, this)
                presenter!!.load(arguments!!.getString("id")!!)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: List<DiscoverDetailRightBean.Item.Data.Content>?) {

    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}