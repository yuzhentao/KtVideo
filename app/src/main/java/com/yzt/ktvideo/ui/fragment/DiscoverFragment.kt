package com.yzt.ktvideo.ui.fragment

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.DiscoverAdapter
import com.yzt.ktvideo.bean.DiscoverBean
import com.yzt.ktvideo.extension.color
import com.yzt.ktvideo.interfaces.OnRvScrollListener
import com.yzt.ktvideo.mvp.contract.DiscoverContract
import com.yzt.ktvideo.mvp.presenter.DiscoverPresenter
import com.yzt.ktvideo.ui.activity.DiscoverDetailActivity
import com.yzt.ktvideo.util.FooterUtil
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 发现
 */
class DiscoverFragment : BaseFragment(), DiscoverContract.View {

    private val adapter: DiscoverAdapter by lazy {
        DiscoverAdapter(null)
    }
    private val presenter: DiscoverPresenter by lazy {
        DiscoverPresenter(context, this)
    }

    private var onRvScrollListener: OnRvScrollListener? = null

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover
    }

    override fun initView() {
        presenter.load()
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: DiscoverBean.Item.Data? = adapter.data[position] as DiscoverBean.Item.Data
            bean?.let {
                val intent = Intent(context, DiscoverDetailActivity::class.java)
                intent.putExtra("id", it.id.toString())
                context?.startActivity(intent)
            }
        }
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var totalDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalDy -= dy
                onRvScrollListener?.onRvScroll(totalDy)
            }
        })
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<DiscoverBean.Item.Data>) {
        adapter.setList(beans)
        adapter.addFooterView(FooterUtil.getFooter(context!!, context!!.color(R.color.app_black)))
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}