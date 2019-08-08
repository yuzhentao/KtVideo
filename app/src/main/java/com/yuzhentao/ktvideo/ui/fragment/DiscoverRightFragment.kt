package com.yuzhentao.ktvideo.ui.fragment

import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailRightPresenter

/**
 * 发现详情-广场
 */
class DiscoverRightFragment : BaseFragment(), DiscoverDetailRightContract.View {

    private var presenter: DiscoverDetailRightPresenter? = null

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

    override fun setData(bean: DiscoverDetailRightBean?) {

    }

}