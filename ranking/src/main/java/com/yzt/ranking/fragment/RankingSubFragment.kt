package com.yzt.ranking.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.bean.RankingSubBean
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.key.Constants
import com.yzt.common.util.FooterUtil
import com.yzt.ranking.R
import com.yzt.ranking.adapter.RankingSubAdapter
import com.yzt.ranking.databinding.FragmentRankingSubBinding
import com.yzt.ranking.viewmodel.RankingSubViewModel
import com.yzt.ranking.viewmodel.RankingSubViewModelFactory

/**
 * 排行-子Fragment
 *
 * @author yzt 2021/2/9
 */
class RankingSubFragment : BaseFragment() {

    private var binding: FragmentRankingSubBinding? = null

    private val adapter: RankingSubAdapter by lazy {
        RankingSubAdapter(null)
    }

    private val viewModel: RankingSubViewModel by lazy {
        ViewModelProvider(this, RankingSubViewModelFactory())[RankingSubViewModel::class.java]
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
        binding!!.rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: RankingSubBean.Item.Data.Content.DataX? =
                adapter.data[position] as RankingSubBean.Item.Data.Content.DataX?
            bean?.let { itt ->
                val id = itt.id
                val img = itt.cover?.feed
                val title = itt.title
                val desc = itt.description
                val duration = itt.duration
                val playUrl = itt.playUrl
                val category = itt.category
                val blurred = itt.cover?.blurred
                val collect = itt.consumption?.collectionCount
                val share = itt.consumption?.shareCount
                val reply = itt.consumption?.replyCount
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
                ARouter
                    .getInstance()
                    .build(Constants.PATH_VIDEO_DETAIL)
                    .withParcelable("bean", videoBean)
                    .withBoolean("showCache", true)
                    .navigation()
            }
        }
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
    }

    override fun initData() {
        arguments?.getString("strategy")?.let {
            viewModel.load(context, it)
            viewModel.liveData.observe(
                this,
                Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> { beans ->
                    beans?.let { itt ->
                        adapter.setList(itt)
                        adapter.addFooterView(
                            FooterUtil.getFooter(
                                requireContext(),
                                requireContext().color(R.color.app_black)
                            )
                        )
                    }
                })
        }
    }

    override fun isLazyLoad(): Boolean {
        return true
    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

}