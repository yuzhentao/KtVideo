package com.yuzhentao.ktvideo.ui.fragment

import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailLeftContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailLeftPresenter

/**
 * 发现详情-推荐
 */
class DiscoverLeftFragment : BaseFragment(), DiscoverDetailLeftContract.View {

    private var presenter: DiscoverDetailLeftPresenter? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover_left
    }

    override fun initView() {
        arguments?.let {
            arguments!!.getString("id")?.let {
                presenter = DiscoverDetailLeftPresenter(context!!, this)
                presenter!!.load(arguments!!.getString("id")!!)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: DiscoverDetailLeftBean?) {

    }

}