package com.yzt.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.HomeAdapter
import com.yzt.ktvideo.bean.HomeBean
import com.yzt.ktvideo.bean.VideoBean
import com.yzt.ktvideo.extension.color
import com.yzt.ktvideo.extension.dimensionPixelOffset
import com.yzt.ktvideo.interfaces.OnRvScrollListener
import com.yzt.ktvideo.mvp.contract.HomeContract
import com.yzt.ktvideo.mvp.presenter.HomePresenter
import com.yzt.ktvideo.ui.activity.VideoDetailActivity
import com.yzt.ktvideo.util.FooterUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

/**
 * 首页
 */
class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var beans: MutableList<HomeBean.Issue.Item> = mutableListOf()
    private val adapter: HomeAdapter by lazy {
        HomeAdapter(null)
    }

    private val presenter: HomePresenter by lazy {
        HomePresenter(context, this)
    }

    private var date: String? = null
    private var isRefresh: Boolean = false

    private var onRvScrollListener: OnRvScrollListener? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        presenter.load()
        srl.setOnRefreshListener(this)
        srl.setColorSchemeResources(R.color.app_pink)
        srl.setProgressViewOffset(
            false,
            context!!.dimensionPixelOffset(R.dimen.x40),
            context!!.dimensionPixelOffset(R.dimen.x80)
        )
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager = rv.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == beans.size - 1) {
                    if (date != null) {
                        presenter.loadMore(date)
                    }
                }
            }
        })
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy -= dy
                onRvScrollListener?.onRvScroll(totalDy)
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
                val videoBean = VideoBean(
                    id,
                    img,
                    title,
                    desc,
                    duration,
                    playUrl,
                    category,
                    blurred,
                    collect,
                    share,
                    reply,
                    time
                )
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
        if (beans.size > 0) {
            adapter.setNewData(beans)
        } else if (adapter.data.size > 0) {
            adapter.addFooterView(
                FooterUtil.getFooter(
                    context!!,
                    context!!.color(R.color.app_black)
                )
            )
        }
    }

    override fun onRefresh() {
        if (!isRefresh) {
            isRefresh = true
            presenter.load()
        }
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}