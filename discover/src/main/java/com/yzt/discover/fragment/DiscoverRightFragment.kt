package com.yzt.discover.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseFragment
import com.yzt.common.extension.color
import com.yzt.common.key.Constants
import com.yzt.common.player.AutoPlayUtil
import com.yzt.common.util.FooterUtil
import com.yzt.discover.R
import com.yzt.discover.activity.DiscoverDetailActivity
import com.yzt.discover.adapter.DiscoverDetailRightAdapter
import com.yzt.discover.databinding.FragmentDiscoverRightBinding
import com.yzt.discover.viewmodel.DiscoverDetailRightViewModel
import com.yzt.discover.viewmodel.DiscoverDetailRightViewModelFactory
import timber.log.Timber

/**
 * 发现详情-广场
 *
 * @author yzt 2021/2/9
 */
class DiscoverRightFragment : BaseFragment() {

    private lateinit var discoverDetailActivity: DiscoverDetailActivity

    private var binding: FragmentDiscoverRightBinding? = null

    private lateinit var layoutManager: LinearLayoutManager
    private val adapter: DiscoverDetailRightAdapter by lazy {
        DiscoverDetailRightAdapter(null)
    }

    private val viewModel: DiscoverDetailRightViewModel by lazy {
        ViewModelProvider(
            this,
            DiscoverDetailRightViewModelFactory()
        )[DiscoverDetailRightViewModel::class.java]
    }

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        discoverDetailActivity = activity as DiscoverDetailActivity
        binding = FragmentDiscoverRightBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.rv.layoutManager = layoutManager
        binding!!.rv.adapter = adapter
        binding!!.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var isScrolled: Boolean = false

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isScrolled) {
                    Timber.e("视频>>>>>onScrolled")
                    isScrolled = true
                    AutoPlayUtil.onVerticalScrollPlayVideo(
                        recyclerView,
                        R.id.cl_video,
                        R.id.player,
                        layoutManager.findFirstVisibleItemPosition(),
                        layoutManager.findLastVisibleItemPosition(),
                        true
                    )
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Timber.e("视频>>>>>onScrollStateChanged")
                    AutoPlayUtil.onVerticalScrollPlayVideo(
                        recyclerView,
                        R.id.cl_video,
                        R.id.player,
                        layoutManager.findFirstVisibleItemPosition(),
                        layoutManager.findLastVisibleItemPosition(),
                        true
                    )
                }
            }
        })
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: DiscoverDetailRightBean.Item.Data.Content? =
                adapter.data[position] as DiscoverDetailRightBean.Item.Data.Content?
            bean?.let {
                val id = it.data?.id
                val img = it.data?.cover?.feed
                val title = discoverDetailActivity.category//都为""，使用大类别
                val desc = it.data?.description
                val duration = it.data?.duration
                val playUrl = it.data?.playUrl
                val category = discoverDetailActivity.category//没有分类字段，使用大类别
                val blurred = it.data?.cover?.blurred
                val collect = it.data?.consumption?.collectionCount
                val share = it.data?.consumption?.shareCount
                val reply = it.data?.consumption?.replyCount
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
        arguments?.let {
            it.getString("id")?.let { itt ->
                viewModel.load(requireContext(), itt)
            }
        }
    }

    override fun initData() {
        viewModel.liveData.observe(
            this,
            Observer<MutableList<DiscoverDetailRightBean.Item.Data.Content>> { beans ->
                adapter.setList(beans)
                adapter.addFooterView(
                    FooterUtil.getFooter(
                        requireContext(),
                        requireContext().color(R.color.app_black)
                    )
                )
            }
        )
    }

    override fun onDestroy() {
        AutoPlayUtil.release()
        super.onDestroy()
    }

    fun scrollToTop() {
        binding!!.rv.smoothScrollToPosition(0)
    }

    /**
     * 恢复播放
     */
    fun resumeVideo() {
        Timber.e(">>>>>DiscoverRightFragment-resume")
        binding?.let {
            AutoPlayUtil.onVerticalScrollPlayVideo(
                it.rv,
                R.id.cl_video,
                R.id.player,
                layoutManager.findFirstVisibleItemPosition(),
                layoutManager.findLastVisibleItemPosition(),
                false
            )
        }
    }

}