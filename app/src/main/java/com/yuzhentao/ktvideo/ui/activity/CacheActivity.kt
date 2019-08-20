package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.CacheAdapter
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.extension.shortToast
import com.yuzhentao.ktvideo.util.DownloadState
import kotlinx.android.synthetic.main.activity_cache.*
import timber.log.Timber

/**
 * 我的缓存
 */
class CacheActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this

    private var beans = ArrayList<VideoBean>()
    private lateinit var adapter: CacheAdapter

    private lateinit var dbManager: VideoDbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_cache)
        dbManager = VideoDbManager()
        Aria.download(this).register()
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

    private fun initView() {
        tv_top.text = getString(R.string.mine_cache)
        iv_top.setOnClickListener(this)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CacheAdapter(beans, dbManager)
        rv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val bean: VideoBean? = adapter!!.data[position] as VideoBean
            bean?.let {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("data", bean)
                intent.putExtra("bundle", bundle)
                intent.putExtra("showCache", false)
                startActivity(intent)
            }
        }
    }

    private fun initData() {
        dbManager.findCache()?.let {
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
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (adapter.data.size > 0) {
            for (index in adapter.data.indices) {
                val bean = adapter.data[index]
                if (task.key == bean.playUrl) {
                    Timber.tag("缓存").e("进度>>>${task.percent}>>>${bean.title}")
                    bean.downloadProgress = task.percent
                    dbManager.update(bean)
                    adapter.notifyItemChanged(index, 1)
                }
            }
        }
    }

    @Download.onTaskFail
    fun onDownloadFail(task: DownloadTask) {
        if (adapter.data.size > 0) {
            for (index in adapter.data.indices) {
                val bean = adapter.data[index]
                if (task.key == bean.playUrl) {
                    Timber.tag("缓存").e("失败>>>${bean.title}")
                    shortToast(getString(R.string.cache_fail))
                    bean.downloadState = DownloadState.ERROR.name
                    bean.downloadProgress = 0
                    dbManager.update(bean)
                    adapter.notifyItemChanged(index, 1)
                }
            }
        }
    }

    @Download.onTaskComplete
    fun onDownloadComplete(task: DownloadTask) {
        if (adapter.data.size > 0) {
            for (index in adapter.data.indices) {
                val bean = adapter.data[index]
                if (task.key == bean.playUrl) {
                    Timber.tag("缓存").e("完成>>>${bean.title}")
                    shortToast(getString(R.string.cache_complete))
                    bean.downloadState = DownloadState.COMPLETE.name
                    bean.downloadProgress = 100
                    dbManager.update(bean)
                    adapter.notifyItemChanged(index, 1)
                }
            }
        }
    }

}