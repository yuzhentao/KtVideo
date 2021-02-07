package com.yzt.ktvideo.ui.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.key.Constant
import com.yzt.common.util.DimenUtil
import com.yzt.common.util.FooterUtil
import com.yzt.common.util.ScrollCalculatorHelper
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.DiscoverDetailRightAdapter
import com.yzt.ktvideo.databinding.FragmentDiscoverRightBinding
import com.yzt.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yzt.ktvideo.mvp.presenter.DiscoverDetailRightPresenter
import com.yzt.ktvideo.ui.activity.DiscoverDetailActivity
import kotlinx.android.synthetic.main.fragment_ranking_sub.*

/**
 * 发现详情-广场
 */
class DiscoverRightFragment : BaseFragment(), DiscoverDetailRightContract.View {

    private lateinit var discoverDetailActivity: DiscoverDetailActivity

    private var binding: FragmentDiscoverRightBinding? = null

    private val adapter: DiscoverDetailRightAdapter by lazy {
        DiscoverDetailRightAdapter(null)
    }

    private val presenter: DiscoverDetailRightPresenter by lazy {
        DiscoverDetailRightPresenter(context!!, this)
    }

    private lateinit var scrollCalculatorHelper: ScrollCalculatorHelper

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        discoverDetailActivity = getActivity() as DiscoverDetailActivity
        binding = FragmentDiscoverRightBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        val playTop = DimenUtil.getHeightInPx() / 2 - DimenUtil.getHeightInPx() / 4
        val playBottom = DimenUtil.getHeightInPx() / 2 + DimenUtil.getHeightInPx() / 4
        scrollCalculatorHelper =
            ScrollCalculatorHelper(R.id.vp, playTop.toInt(), playBottom.toInt())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem: Int? = 0
            var lastVisibleItem: Int? = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (!discoverDetailActivity.isFull) {
                    scrollCalculatorHelper.onScroll(
                        recyclerView,
                        firstVisibleItem!!,
                        lastVisibleItem!!,
                        lastVisibleItem!! - firstVisibleItem!!
                    )
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState)
            }
        })
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: DiscoverDetailRightBean.Item.Data.Content? =
                adapter.data[position] as DiscoverDetailRightBean.Item.Data.Content
            bean?.let {
                val id = it.data?.id
                val img = it.data?.cover?.feed
                val title = discoverDetailActivity.category//都为""，使用大类别
                val desc = it.data?.description
                val duration = it.data?.duration
                val playUrl = it.data?.playUrl
                val category = discoverDetailActivity.category//没有分类字段，使用大类别
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
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        arguments?.let {
            it.getString("id")?.let { itt ->
                presenter.load(itt)
            }
        }
    }

    override fun initData() {

    }

    override fun setData(beans: MutableList<DiscoverDetailRightBean.Item.Data.Content>?) {
        adapter.setList(beans)
        adapter.addFooterView(FooterUtil.getFooter(context!!, context!!.color(R.color.app_black)))
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}