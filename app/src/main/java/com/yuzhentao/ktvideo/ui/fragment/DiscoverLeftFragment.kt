package com.yuzhentao.ktvideo.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.DiscoverDetailLeftAdapter
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailLeftContract
import com.yuzhentao.ktvideo.mvp.presenter.DiscoverDetailLeftPresenter
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 发现详情-推荐
 */
class DiscoverLeftFragment : BaseFragment(), DiscoverDetailLeftContract.View {

    private var presenter: DiscoverDetailLeftPresenter? = null

    lateinit var adapterDetail: DiscoverDetailLeftAdapter
    private var beans: ArrayList<DiscoverDetailLeftBean.Item.Data> = ArrayList()

    override fun getLayoutResources(): Int {
        return R.layout.fragment_discover_left
    }

    override fun initView() {
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterDetail = DiscoverDetailLeftAdapter(context, beans)
        rv.adapter = adapterDetail
        arguments?.let {
            arguments!!.getString("id")?.let {
                presenter = DiscoverDetailLeftPresenter(context!!, this)
                presenter!!.load(arguments!!.getString("id")!!)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: DiscoverDetailLeftBean?) {
        if (beans.size > 0) {
            beans.clear()
        }
        bean?.let {
            bean.itemList.forEach {
                beans.add(it.data)
            }
            adapterDetail.notifyDataSetChanged()
        }
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}