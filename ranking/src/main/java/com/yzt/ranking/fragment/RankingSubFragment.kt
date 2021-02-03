package com.yzt.ranking.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.bean.RankingSubBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.util.FooterUtil
import com.yzt.ranking.R
import com.yzt.ranking.adapter.RankingSubAdapter
import com.yzt.ranking.databinding.FragmentRankingSubBinding
import com.yzt.ranking.viewmodel.RankingSubViewModel

/**
 * 排行
 */
class RankingSubFragment : BaseFragment() {

    private var binding: FragmentRankingSubBinding? = null

    private val adapter: RankingSubAdapter by lazy {
        RankingSubAdapter(null)
    }

    private val viewModel: RankingSubViewModel by lazy {
        ViewModelProviders.of(this).get(RankingSubViewModel::class.java)
    }

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentRankingSubBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        arguments?.getString("strategy")?.let {
            viewModel.load(context, it)
            viewModel.liveData.observe(
                this,
                Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> { beans ->
                    beans?.let { itt ->
                        adapter.setList(itt)
                        adapter.addFooterView(
                            FooterUtil.getFooter(
                                context!!,
                                context!!.color(R.color.app_black)
                            )
                        )
                    }
                })
        }
        binding!!.rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
//            val bean: RankingSubBean.Item.Data.Content.DataX? =
//                adapter.data[position] as RankingSubBean.Item.Data.Content.DataX
//            bean?.let { itt ->
//                val intent = Intent(context, VideoDetailActivity::class.java)
//                val id = itt.id
//                val img = itt.cover?.feed
//                val title = itt.title
//                val desc = itt.description
//                val duration = itt.duration
//                val playUrl = itt.playUrl
//                val category = itt.category
//                val blurred = itt.cover?.blurred
//                val collect = itt.consumption?.collectionCount
//                val share = itt.consumption?.shareCount
//                val reply = itt.consumption?.replyCount
//                val time = System.currentTimeMillis()
//                val videoBean = VideoBean(
//                    id,
//                    img,
//                    title,
//                    desc,
//                    duration,
//                    playUrl,
//                    category,
//                    blurred,
//                    collect,
//                    share,
//                    reply,
//                    time
//                )
//                val bundle = Bundle()
//                bundle.putParcelable("data", videoBean)
//                intent.putExtra("bundle", bundle)
//                intent.putExtra("showCache", true)
//                startActivity(intent)
//            }
        }
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
    }

    override fun initData() {

    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

}