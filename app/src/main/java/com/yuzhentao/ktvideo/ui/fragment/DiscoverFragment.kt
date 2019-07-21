package com.yuzhentao.ktvideo.ui.fragment

import android.support.v7.widget.GridLayoutManager
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverAdapter
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverPresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 发现
 */
class DiscoverFragment : BaseFragment(), DiscoverContract.View {

    var presenter: DiscoverPresenter? = null
    var adapter: DiscoverAdapter? = null
    var beans: MutableList<DiscoverBean> = mutableListOf()

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover
    }

    override fun initView() {
        presenter = DiscoverPresenter(context, this)
        presenter?.load()
        rv.layoutManager = GridLayoutManager(context, 2)
        adapter = DiscoverAdapter(context, beans)
        rv.adapter = adapter
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<DiscoverBean>) {
        if (this.beans.size > 0) {
            this.beans.clear()
        }
        this.beans.addAll(beans)
        adapter?.notifyDataSetChanged()
    }

}