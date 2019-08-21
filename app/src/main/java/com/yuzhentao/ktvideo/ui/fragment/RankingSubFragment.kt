package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingSubAdapter
import com.yuzhentao.ktvideo.bean.RankingSubBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.mvp.contract.RankingSubContract
import com.yuzhentao.ktvideo.mvp.presenter.RankingSubPresenter
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import kotlinx.android.synthetic.main.fragment_ranking.*

/**
 * 排行
 */
class RankingSubFragment : BaseFragment(), RankingSubContract.View {

    private lateinit var presenter: RankingSubPresenter
    private lateinit var adapter: RankingSubAdapter

    override fun getLayoutResources(): Int {
        return R.layout.fragment_ranking
    }

    override fun initView() {
        arguments?.getString("strategy")?.let {
            presenter = RankingSubPresenter(context, this)
            presenter.load(arguments!!.getString("strategy")!!)
        }
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RankingSubAdapter(null)
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: RankingSubBean.Item.Data.Content.DataX? = adapter!!.data[position] as RankingSubBean.Item.Data.Content.DataX
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
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun setData(beans: MutableList<RankingSubBean.Item.Data.Content.DataX>?) {
        beans?.let {
            adapter.setNewData(beans)
        }
    }

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}