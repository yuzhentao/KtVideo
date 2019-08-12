package com.yuzhentao.ktvideo.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverDetailLeftAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailLeftContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailLeftPresenter
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
import com.yuzhentao.ktvideo.util.DimenUtil
import com.yuzhentao.ktvideo.util.NetworkUtil
import com.yuzhentao.ktvideo.util.ScrollCalculatorHelper
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 发现详情-推荐
 */
class DiscoverLeftFragment : BaseFragment(), DiscoverDetailLeftContract.View {

    lateinit var activity: DiscoverDetailActivity

    private var presenter: DiscoverDetailLeftPresenter? = null

    lateinit var adapter: DiscoverDetailLeftAdapter
    lateinit var scrollCalculatorHelper: ScrollCalculatorHelper

    override fun getLayoutResources(): Int {
        activity = getActivity() as DiscoverDetailActivity
        return R.layout.fragment_discover_left
    }

    override fun initView() {
        NetworkUtil.isConnected()
        NetworkUtil.isWifi()
        NetworkUtil.isCellular()
        val playTop = DimenUtil.getHeightInPx() / 2 - DimenUtil.getHeightInPx() / 4
        val playBottom = DimenUtil.getHeightInPx() / 2 + DimenUtil.getHeightInPx() / 4
        scrollCalculatorHelper = ScrollCalculatorHelper(R.id.vp, playTop.toInt(), playBottom.toInt())

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapter = DiscoverDetailLeftAdapter(R.layout.item_discover_detail, null)
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem: Int? = 0
            var lastVisibleItem: Int? = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!activity.isFull) {
                    scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem!!, lastVisibleItem!!, lastVisibleItem!! - firstVisibleItem!!)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState)
            }
        })
        arguments?.let {
            arguments!!.getString("id")?.let {
                presenter = DiscoverDetailLeftPresenter(context!!, this)
                presenter!!.load(arguments!!.getString("id")!!)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: List<DiscoverDetailLeftBean.Item.Data.Content>?) {
            adapter.setNewData(beans)
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}