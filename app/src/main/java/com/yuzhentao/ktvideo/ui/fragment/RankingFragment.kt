package com.yuzhentao.ktvideo.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingAdapter
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.mvp.contract.HotContract
import com.yuzhentao.ktvideo.mvp.presenter.HotPresenter
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 排行
 */
class RankingFragment : BaseFragment(), HotContract.View {

    lateinit var presenter: HotPresenter
    lateinit var strategy: String
    lateinit var adapter: RankingAdapter
    private var beans: ArrayList<HotBean.Item.Data> = ArrayList()

    override fun getLayoutResources(): Int {
        return R.layout.fragment_ranking
    }

    override fun initView() {
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RankingAdapter(context, beans)
        rv.adapter = adapter
        if (arguments != null) {
            strategy = arguments!!.getString("strategy")!!
            presenter = HotPresenter(context, this)
            presenter.load(strategy)
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: HotBean) {
        if (beans.size > 0) {
            beans.clear()
        }
        bean.itemList.forEach {
            beans.add(it.data)
        }
        adapter.notifyDataSetChanged()
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}