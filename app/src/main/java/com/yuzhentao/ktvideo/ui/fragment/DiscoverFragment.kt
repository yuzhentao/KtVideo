package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverAdapter
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.interfaces.OnRvScrollListener
import com.yuzhentao.ktvideo.mvp.contract.DiscoverContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverPresenter
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
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
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: DiscoverBean.Item.Data? = adapter!!.data[position] as DiscoverBean.Item.Data
            bean?.let {
                val intent = Intent(context, DiscoverDetailActivity::class.java)
                intent.putExtra("id", bean.id.toString())
                context?.startActivity(intent)
            }
        }
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
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
        adapter.setNewData(beans)
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

    fun setOnRvScrollListener(onRvScrollListener: OnRvScrollListener) {
        this.onRvScrollListener = onRvScrollListener
    }

}