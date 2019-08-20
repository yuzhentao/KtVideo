package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HomeAdapter
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.mvp.contract.HomeContract
import com.yuzhentao.ktvideo.mvp.presenter.HomePresenter
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

/**
 * 首页
 */
class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var isRefresh: Boolean = false
    private lateinit var adapter: HomeAdapter
    private lateinit var beans: MutableList<HomeBean.Issue.Item>
    var presenter: HomePresenter? = null
    var date: String? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        presenter = HomePresenter(context, this)
        presenter?.load()
        srl.setOnRefreshListener(this)
        srl.setColorSchemeResources(R.color.app_pink)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = HomeAdapter(null)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = rv.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == beans.size - 1) {
                    if (date != null) {
                        presenter?.loadMore(date)
                    }
                }
            }
        })
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: HomeBean.Issue.Item? = adapter!!.data[position] as HomeBean.Issue.Item
            bean?.let {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val id = bean.data?.id
                val img = bean.data?.cover?.feed
                val title = bean.data?.title
                val duration = bean.data?.duration
                val desc = bean.data?.description
                val playUrl = bean.data?.playUrl
                val category = bean.data?.category
                val blurred = bean.data?.cover?.blurred
                val collect = bean.data?.consumption?.collectionCount
                val share = bean.data?.consumption?.shareCount
                val reply = bean.data?.consumption?.replyCount
                val time = System.currentTimeMillis()
                val videoBean = VideoBean(id, img, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
                val bundle = Bundle()
                bundle.putParcelable("data", videoBean)
                intent.putExtra("bundle", bundle)
                intent.putExtra("showCache", true)
                startActivity(intent)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: HomeBean) {
        beans = mutableListOf()
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl)
        date = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isRefresh) {
            isRefresh = false
            srl.isRefreshing = false
            if (beans.size > 0) {
                beans.clear()
            }
        }
        bean.issueList
                ?.flatMap { it.itemList!! }
                ?.filter { it.type == "video" }
                ?.forEach { beans.add(it) }
        adapter.setNewData(beans)
    }

    override fun onRefresh() {
        if (!isRefresh) {
            isRefresh = true
            presenter?.load()
        }
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}