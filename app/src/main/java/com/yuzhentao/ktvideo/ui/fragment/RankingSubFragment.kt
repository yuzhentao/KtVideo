package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.RankingSubAdapter
import com.yuzhentao.ktvideo.bean.RankingSubBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.extension.color
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.FooterUtil
import com.yuzhentao.ktvideo.viewmodel.RankingSubViewModel
import kotlinx.android.synthetic.main.fragment_ranking_sub.*

/**
 * 排行
 */
class RankingSubFragment : BaseFragment() {

    private val adapter: RankingSubAdapter by lazy {
        RankingSubAdapter(null)
    }

    private val viewModel: RankingSubViewModel by lazy {
        ViewModelProviders.of(this).get(RankingSubViewModel::class.java)
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_ranking_sub
    }

    override fun initView() {
        arguments?.getString("strategy")?.let {
            viewModel.load(context, arguments!!.getString("strategy")!!)
            viewModel.liveData.observe(
                this,
                Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> { beans ->
                    beans?.let {
                        adapter.setNewData(beans)
                        adapter.addFooterView(
                            FooterUtil.getFooter(
                                context!!,
                                context!!.color(R.color.app_black)
                            )
                        )
                    }
                })
        }
        rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: RankingSubBean.Item.Data.Content.DataX? =
                adapter!!.data[position] as RankingSubBean.Item.Data.Content.DataX
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

    fun scrollToTop() {
        rv.smoothScrollToPosition(0)
    }

}