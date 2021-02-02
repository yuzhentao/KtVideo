package com.yzt.discover.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.bean.DiscoverBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.listener.OnRvScrollListener
import com.yzt.common.util.FooterUtil
import com.yzt.discover.R
import com.yzt.discover.adapter.DiscoverAdapter
import com.yzt.discover.databinding.FragmentDiscoverBinding
import com.yzt.discover.mvp.contract.DiscoverContract
import com.yzt.discover.mvp.presenter.DiscoverPresenter

/**
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
        binding!!.rv.layoutManager = GridLayoutManager(context, 2)
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: DiscoverBean.Item.Data? = adapter.data[position] as DiscoverBean.Item.Data
            bean?.let {
//                val intent = Intent(context, DiscoverDetailActivity::class.java)
//                intent.putExtra("id", it.id.toString())
//                context?.startActivity(intent)
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
        adapter.addFooterView(FooterUtil.getFooter(context!!, context!!.color(R.color.app_black)))
    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}