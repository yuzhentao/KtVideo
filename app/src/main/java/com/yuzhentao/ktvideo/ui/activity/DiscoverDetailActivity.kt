package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailPresenter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 发现详情
 */
class DiscoverDetailActivity : AppCompatActivity(), DiscoverDetailContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var context: Context = this
    private var activity: DiscoverDetailActivity = this

    private var adapter: RankingAdapter? = null

    private var presenter: DiscoverDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_detail)
        ImmersionBar.with(activity).transparentBar().barAlpha(0.3F).fitsSystemWindows(true).init()
        initView()
    }

    private fun initView() {
        presenter = DiscoverDetailPresenter(context, this)
        presenter?.load("12")
        srl.setOnRefreshListener(this)
        srl.setColorSchemeResources(R.color.pink)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        adapter = RankingAdapter()
//        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = rv.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == beans.size - 1) {
//                    if (date != null) {
//                        presenter?.loadMore(date)
//                    }
//                }
            }
        })
    }

    override fun setData(beans: DiscoverDetailBean) {

    }

    override fun onRefresh() {

    }

}