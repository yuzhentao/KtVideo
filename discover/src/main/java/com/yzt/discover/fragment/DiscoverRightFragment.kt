package com.yzt.discover.fragment

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
import com.yzt.discover.R
import com.yzt.discover.activity.DiscoverDetailActivity
import com.yzt.discover.adapter.DiscoverDetailRightAdapter
import com.yzt.discover.databinding.FragmentDiscoverRightBinding
import com.yzt.discover.mvp.contract.DiscoverDetailRightContract
import com.yzt.discover.mvp.presenter.DiscoverDetailRightPresenter

/**
 * 发现详情-广场
 *
 * @author yzt 2021/2/9
 */
class DiscoverRightFragment : BaseFragment(), DiscoverDetailRightContract.View {

    private lateinit var discoverDetailActivity: DiscoverDetailActivity

    private var binding: FragmentDiscoverRightBinding? = null

    private val adapter: DiscoverDetailRightAdapter by lazy {
        DiscoverDetailRightAdapter(null)
    }

    private val presenter: DiscoverDetailRightPresenter by lazy {
        DiscoverDetailRightPresenter(requireContext(), this)
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
        val playTop = DimenUtil.getHeightInPx(requireContext()) / 2 - DimenUtil.getHeightInPx(requireContext()) / 4
        val playBottom = DimenUtil.getHeightInPx(requireContext()) / 2 + DimenUtil.getHeightInPx(requireContext()) / 4
        scrollCalculatorHelper =
            ScrollCalculatorHelper(R.id.vp, playTop.toInt(), playBottom.toInt())
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.rv.layoutManager = layoutManager
        binding!!.rv.adapter = adapter
        binding!!.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        adapter.addFooterView(FooterUtil.getFooter(requireContext(), requireContext().color(R.color.app_black)))
    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

}