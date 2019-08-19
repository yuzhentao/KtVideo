package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverAdapter
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverPresenter
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * 发现
 */
class DiscoverFragment : BaseFragment(), DiscoverContract.View {

    private var presenter: DiscoverPresenter? = null
    private lateinit var adapter: DiscoverAdapter
    private var beans: MutableList<DiscoverBean> = mutableListOf()

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover
    }

    override fun initView() {
        presenter = DiscoverPresenter(context, this)
        presenter?.load()
        rv.layoutManager = GridLayoutManager(context, 2)
        adapter = DiscoverAdapter(null)
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: DiscoverBean? = adapter!!.data[position] as DiscoverBean
            bean?.let {
                val intent = Intent(context, DiscoverDetailActivity::class.java)
                intent.putExtra("id", bean.tagId.toString())
                context?.startActivity(intent)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<DiscoverBean>) {
        if (this.beans.size > 0) {
            this.beans.clear()
        }
        this.beans.addAll(beans)
        adapter.setNewData(beans)
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}