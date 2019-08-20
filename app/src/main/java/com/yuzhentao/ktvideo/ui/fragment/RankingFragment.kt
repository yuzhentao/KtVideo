package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingAdapter
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.mvp.contract.HotContract
import com.yuzhentao.ktvideo.mvp.presenter.HotPresenter
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 排行
 */
class RankingFragment : BaseFragment(), HotContract.View {

    lateinit var presenter: HotPresenter
    lateinit var strategy: String
    private lateinit var adapter: RankingAdapter
    private var beans: ArrayList<HotBean.Item.Data> = ArrayList()

    override fun getLayoutResources(): Int {
        return R.layout.fragment_ranking
    }

    override fun initView() {
        if (arguments != null) {
            strategy = arguments!!.getString("strategy")!!
            presenter = HotPresenter(context, this)
            presenter.load(strategy)
        }
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RankingAdapter(beans)
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: HotBean.Item.Data? = adapter!!.data[position] as HotBean.Item.Data
            bean?.let {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val id = bean.id
                val img = bean.cover?.feed
                val title = bean.title
                val desc = bean.description
                val duration = bean.duration
                val playUrl = bean.playUrl
                val category = bean.category
                val blurred = bean.cover?.blurred
                val collect = bean.consumption?.collectionCount
                val share = bean.consumption?.shareCount
                val reply = bean.consumption?.replyCount
                val time = System.currentTimeMillis()
                val videoBean = VideoBean(id, img, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
                val bundle = Bundle()
                bundle.putParcelable("data", videoBean)
                intent.putExtra("bundle", bundle)
                intent.putExtra("showCache", true)
                startActivity(intent)
            }
        }
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(bean: HotBean) {
        if (beans.size > 0) {
            beans.clear()
        }
        bean.itemList?.forEach {
            beans.add(it.data!!)
        }
        adapter.setNewData(beans)
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}