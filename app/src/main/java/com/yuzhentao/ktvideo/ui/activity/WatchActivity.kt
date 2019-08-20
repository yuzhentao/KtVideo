package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.WatchAdapter
import com.yuzhentao.ktvideo.bean.SearchBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.mvp.contract.SearchContract
import com.yuzhentao.ktvideo.mvp.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 观看记录
 */
class WatchActivity : AppCompatActivity(), View.OnClickListener, SearchContract.View {

    var context: Context = this
    var activity: WatchActivity = this

    var beans = ArrayList<VideoBean>()

    lateinit var adapter: WatchAdapter

    private lateinit var dbManager: VideoDbManager

    private var presenter: SearchPresenter? = null

    private var noKey: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_watch)
        dbManager = VideoDbManager()
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
                onBackPressed()
            }
        }
    }

    override fun setData(beans: ArrayList<SearchBean.Item.Data.Content>) {
        beans.forEach {
            val data = it.data
            data?.let {
                this.beans.add(VideoBean(data.id,
                        data.cover?.feed,
                        data.title,
                        data.description,
                        data.duration,
                        data.playUrl,
                        data.category,
                        data.cover?.blurred,
                        data.consumption?.collectionCount,
                        data.consumption?.shareCount,
                        data.consumption?.replyCount))
            }
        }
        adapter.setNewData(this.beans)
    }

    private fun initView() {
        noKey = intent.getStringExtra("key").isNullOrEmpty()
        if (noKey!!) {
            tv_top.text = getString(R.string.mine_cache)
        } else {
            tv_top.text = intent.getStringExtra("key")
        }
        iv_top.setOnClickListener(this)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = WatchAdapter(null)
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: VideoBean? = adapter!!.data[position] as VideoBean
            bean?.let {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("data", bean)
                intent.putExtra("bundle", bundle)
                intent.putExtra("showCache", !noKey!!)
                context.startActivity(intent)
            }
        }
    }

    private fun initData() {
        if (noKey!!) {
            dbManager.findAll()?.let {
                if (beans.size > 0) {
                    beans.clear()
                }
                beans.addAll(it)
                if (beans.size > 0) {
                    rv.visibility = View.VISIBLE
                    tv_hint.visibility = View.GONE
                } else {
                    rv.visibility = View.GONE
                    tv_hint.visibility = View.VISIBLE
                }
                adapter.setNewData(beans)
            }
        } else {
            presenter = SearchPresenter(context, this)
            presenter?.load(intent.getStringExtra("key"))
        }
    }

}