package com.yzt.discover.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ImmersionBar
import com.yzt.bean.DiscoverBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.key.Constant
import com.yzt.common.listener.OnRvScrollListener
import com.yzt.common.util.DimenUtil
import com.yzt.common.util.FooterUtil
import com.yzt.common.util.ViewUtil
import com.yzt.discover.R
import com.yzt.discover.adapter.DiscoverAdapter
import com.yzt.discover.databinding.FragmentDiscoverBinding
import com.yzt.discover.mvp.contract.DiscoverContract
import com.yzt.discover.mvp.presenter.DiscoverPresenter

/**
 * 发现
 *
 * @author yzt 2020/12/31
 */
class DiscoverFragment : BaseFragment(), DiscoverContract.View {

    private var binding: FragmentDiscoverBinding? = null

    private val adapter: DiscoverAdapter by lazy {
        DiscoverAdapter(null)
    }
    private val presenter: DiscoverPresenter by lazy {
        DiscoverPresenter(context, this)
    }

    private var onRvScrollListener: OnRvScrollListener? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentDiscoverBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        presenter.load()
        ViewUtil.setPaddings(
            binding!!.rv,
            0,
            ImmersionBar.getStatusBarHeight(this) + DimenUtil.dp2px(requireContext(), 48),
            0,
            0,
        )
        binding!!.rv.layoutManager = GridLayoutManager(context, 2)
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: DiscoverBean.Item.Data? = adapter.data[position] as DiscoverBean.Item.Data?
            bean?.let {
                ARouter
                    .getInstance()
                    .build(Constant.PATH_DISCOVER_DETAIL)
                    .withString("id", it.id.toString())
                    .navigation()
            }
        }
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        binding!!.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy -= dy
                onRvScrollListener?.onRvScroll(totalDy)
            }
        })
    }

    override fun initData() {

    }

    override fun setData(beans: MutableList<DiscoverBean.Item.Data>) {
        adapter.setList(beans)
        adapter.addFooterView(FooterUtil.getFooter(requireContext(), requireContext().color(R.color.app_black)))
    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}