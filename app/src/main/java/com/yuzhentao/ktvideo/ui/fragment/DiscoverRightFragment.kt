package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverDetailRightAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailRightPresenter
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.DimenUtil
import com.yuzhentao.ktvideo.util.ScrollCalculatorHelper
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 发现详情-广场
 */
class DiscoverRightFragment : BaseFragment(), DiscoverDetailRightContract.View {

    lateinit var activity: DiscoverDetailActivity

    private var presenter: DiscoverDetailRightPresenter? = null

    lateinit var adapter: DiscoverDetailRightAdapter
    lateinit var scrollCalculatorHelper: ScrollCalculatorHelper

    override fun getLayoutResources(): Int {
        activity = getActivity() as DiscoverDetailActivity
        return R.layout.fragment_discover_right
    }

    override fun initView() {
        val playTop = DimenUtil.getHeightInPx() / 2 - DimenUtil.getHeightInPx() / 4
        val playBottom = DimenUtil.getHeightInPx() / 2 + DimenUtil.getHeightInPx() / 4
        scrollCalculatorHelper = ScrollCalculatorHelper(R.id.vp, playTop.toInt(), playBottom.toInt())

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = layoutManager
        adapter = DiscoverDetailRightAdapter(null)
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
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: DiscoverDetailRightBean.Item.Data.Content? = adapter!!.data[position] as DiscoverDetailRightBean.Item.Data.Content
            bean?.let {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val id = bean.data?.id
                val img = bean.data?.cover?.feed
                val title = activity.category//都为""，使用大类别
                val desc = bean.data?.description
                val duration = bean.data?.duration
                val playUrl = bean.data?.playUrl
                val category = activity.category//没有分类字段，使用大类别
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
                activity.startActivity(intent)
            }
        }
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        arguments?.let {
            arguments!!.getString("id")?.let {
                presenter = DiscoverDetailRightPresenter(context!!, this)
                presenter!!.load(arguments!!.getString("id")!!)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<DiscoverDetailRightBean.Item.Data.Content>?) {
        adapter.setNewData(beans)
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}