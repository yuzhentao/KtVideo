package com.yzt.home.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.yzt.bean.HomeBean
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.key.Constant
import com.yzt.common.listener.OnRvScrollListener
import com.yzt.common.util.FooterUtil
import com.yzt.home.R
import com.yzt.home.adapter.HomeAdapter
import com.yzt.home.databinding.FragmentHomeBinding
import com.yzt.home.mvp.contract.HomeContract
import com.yzt.home.mvp.presenter.HomePresenter
import java.util.regex.Pattern

/**
 * 首页
 *
 * @author yzt 2020/12/31
 */
class HomeFragment : BaseFragment(), HomeContract.View, SwipeRefreshLayout.OnRefreshListener {

    private var binding: FragmentHomeBinding? = null

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

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        presenter.load()
        binding!!.srl.setOnRefreshListener(this)
        binding!!.srl.setColorSchemeResources(R.color.app_pink)
        binding!!.srl.setProgressViewOffset(
            false,
            requireContext().dimensionPixelOffset(R.dimen.x40),
            requireContext().dimensionPixelOffset(R.dimen.x80)
        )
        binding!!.rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.rv.adapter = adapter
        binding!!.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager: LinearLayoutManager =
                    binding!!.rv.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == beans.size - 1) {
                    if (date != null) {
                        presenter.loadMore(date)
                    }
                }
            }
        })
        binding!!.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy -= dy
                onRvScrollListener?.onRvScroll(totalDy)
            }
        })
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: HomeBean.Issue.Item? = adapter.data[position] as HomeBean.Issue.Item?
            bean?.let {
                val id = it.data?.id
                val img = it.data?.cover?.feed
                val title = it.data?.title
                val duration = it.data?.duration
                val desc = it.data?.description
                val playUrl = it.data?.playUrl
                val category = it.data?.category
                val blurred = it.data?.cover?.blurred
                val collect = it.data?.consumption?.collectionCount
                val share = it.data?.consumption?.shareCount
                val reply = it.data?.consumption?.replyCount
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
                ARouter
                    .getInstance()
                    .build(Constant.PATH_VIDEO_DETAIL)
                    .withParcelable("bean", videoBean)
                    .withBoolean("showCache", true)
                    .navigation()
            }
        }
    }

    override fun initData() {

    }

    override fun setData(bean: HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean.nextPageUrl)
        date = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (isRefresh) {
            isRefresh = false
            binding!!.srl.isRefreshing = false
            if (beans.size > 0) {
                beans.clear()
            }
        }
        bean.issueList
            ?.flatMap { it.itemList!! }
            ?.filter { it.type == "video" }
            ?.forEach { beans.add(it) }
        if (beans.size > 0) {
            adapter.setList(beans)
        } else if (adapter.data.size > 0) {
            adapter.addFooterView(
                FooterUtil.getFooter(
                    requireContext(),
                    requireContext().color(R.color.app_black)
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
        binding!!.rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}