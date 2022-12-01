package com.yzt.ktvideo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.bean.SearchBean
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.FooterUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.WatchAdapter
import com.yzt.ktvideo.databinding.ActivityWatchBinding
import com.yzt.ktvideo.mvp.contract.SearchContract
import com.yzt.ktvideo.mvp.presenter.SearchPresenter

/**
 * 观看记录
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_WATCH)
class WatchActivity : BaseAppCompatActivity(), View.OnClickListener, SearchContract.View {

    private var binding: ActivityWatchBinding? = null

    private lateinit var beans: MutableList<VideoBean>
    private val adapter: WatchAdapter by lazy {
        WatchAdapter(null)
    }

    private val presenter: SearchPresenter by lazy {
        SearchPresenter(context!!, this)
    }

    private val dbManager: VideoDbManager by lazy {
        VideoDbManager()
    }

    private var noKey: Boolean? = true

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityWatchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
        noKey = intent.getStringExtra("key").isNullOrEmpty()
        if (noKey!!) {
            binding!!.tvTop.text = getString(R.string.mine_watch)
        } else {
            binding!!.tvTop.text = intent.getStringExtra("key")
        }
        binding!!.rv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: VideoBean? = adapter.data[position] as VideoBean?
            bean?.let {
                ARouter
                    .getInstance()
                    .build(Constant.PATH_VIDEO_DETAIL)
                    .withParcelable("bean", it)
                    .withBoolean("showCache", !noKey!!)
                    .navigation()
            }
        }
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
    }

    override fun initData(savedInstanceState: Bundle?) {
        beans = mutableListOf()
        if (noKey!!) {
            dbManager.findAll()?.let {
                beans.addAll(it)
                if (beans.size > 0) {
                    binding!!.rv.visibility = View.VISIBLE
                    binding!!.tvHint.visibility = View.GONE
                } else {
                    binding!!.rv.visibility = View.GONE
                    binding!!.tvHint.visibility = View.VISIBLE
                }
                adapter.setList(beans)
                adapter.addFooterView(
                    FooterUtil.getFooter(
                        context!!,
                        color(R.color.app_black)
                    )
                )
            }
        } else {
            intent.getStringExtra("key")?.let {
                presenter.load(it)
            }
        }
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressed()
                }
            }
        }
    }

    override fun setData(beans: MutableList<SearchBean.Item.Data.Content>) {
        beans.forEach {
            val data = it.data
            data?.let { itt ->
                this.beans.add(
                    VideoBean(
                        itt.id,
                        itt.cover?.feed,
                        itt.title,
                        itt.description,
                        itt.duration,
                        itt.playUrl,
                        itt.category,
                        itt.cover?.blurred,
                        itt.consumption?.collectionCount,
                        itt.consumption?.shareCount,
                        itt.consumption?.replyCount
                    )
                )
            }
        }
        adapter.setList(this.beans)
    }

}