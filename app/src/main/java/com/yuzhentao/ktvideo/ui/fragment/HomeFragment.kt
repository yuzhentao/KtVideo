package com.yuzhentao.ktvideo.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.mvp.contract.HomeContract
import com.yuzhentao.ktvideo.mvp.presenter.HomePresenter

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    var isRefresh: Boolean = false
    var presenter: HomePresenter? = null
    var beans: ArrayList<HomeBean.IssueListBean.ItemListBean>? = null
//    var adapter:

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: HomeBean) {

    }

    override fun onRefresh() {

    }

}