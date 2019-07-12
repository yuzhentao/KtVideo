package com.yuzhentao.ktvideo.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HomeAdapter
import com.yuzhentao.ktvideo.bean.Item
import com.yuzhentao.ktvideo.bean.NewHomeBean
import com.yuzhentao.ktvideo.mvp.contract.HomeContract
import com.yuzhentao.ktvideo.mvp.presenter.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var isRefresh: Boolean = false
    var presenter: HomePresenter? = null
    var beans = ArrayList<Item>()
    private var adapter: HomeAdapter? = null
    var data: String? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        srl.setOnRefreshListener(this)
        srl.setColorSchemeResources(R.color.orange)
        presenter = HomePresenter(context, this)
        presenter?.requestData()
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = HomeAdapter(context, beans)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = rv.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == beans.size - 1) {
                    if (data != null) {
                        presenter?.requestMoreData(data)
                    }
                }
            }
        })
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: NewHomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isRefresh) {
            isRefresh = false
            srl.isRefreshing = false
            if (beans.size > 0) {
                beans.clear()
            }
        }
        bean.issueList
                .flatMap { it.itemList }
                .filter { it.type == "video" }
                .forEach { beans.add(it) }
        adapter?.notifyDataSetChanged()
    }

    override fun onRefresh() {
        if (!isRefresh) {
            isRefresh = true
            presenter?.requestData()
        }
    }

}