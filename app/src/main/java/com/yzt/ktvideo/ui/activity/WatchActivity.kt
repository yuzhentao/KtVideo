package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.bean.SearchBean
import com.yzt.bean.VideoBean
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.FooterUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.WatchAdapter
import com.yzt.ktvideo.mvp.contract.SearchContract
import com.yzt.ktvideo.mvp.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 观看记录
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_WATCH)
class WatchActivity : AppCompatActivity(), View.OnClickListener, SearchContract.View {

    private var context: Context = this

    private lateinit var beans: MutableList<VideoBean>
    private val adapter: WatchAdapter by lazy {
        WatchAdapter(null)
    }

    private val presenter: SearchPresenter by lazy {
        SearchPresenter(context, this)
    }
    private val dbManager: VideoDbManager by lazy {
        VideoDbManager()
    }

    private var noKey: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_watch)
        initView()
        initData()
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

    private fun initView() {
        noKey = intent.getStringExtra("key").isNullOrEmpty()
        if (noKey!!) {
            tv_top.text = getString(R.string.mine_watch)
        } else {
            tv_top.text = intent.getStringExtra("key")
        }
        iv_top.setOnClickListener(this)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: VideoBean? = adapter.data[position] as VideoBean
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

    private fun initData() {
        beans = mutableListOf()
        if (noKey!!) {
            dbManager.findAll()?.let {
                beans.addAll(it)
                if (beans.size > 0) {
                    rv.visibility = View.VISIBLE
                    tv_hint.visibility = View.GONE
                } else {
                    rv.visibility = View.GONE
                    tv_hint.visibility = View.VISIBLE
                }
                adapter.setList(beans)
                adapter.addFooterView(
                    FooterUtil.getFooter(
                        context,
                        context.color(R.color.app_black)
                    )
                )
            }
        } else {
            intent.getStringExtra("key")?.let {
                presenter.load(it)
            }
        }
    }

}