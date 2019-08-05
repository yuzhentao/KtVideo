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
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.CacheAdapter
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.interfaces.OnItemClickListener
import com.yuzhentao.ktvideo.util.DownloadState
import com.yuzhentao.ktvideo.util.shortToast
import kotlinx.android.synthetic.main.activity_cache.*
import timber.log.Timber

/**
 * 我的缓存
 */
class CacheActivity : AppCompatActivity() {

    var context: Context = this
    var activity: CacheActivity = this

    var beans = ArrayList<VideoBean>()

    lateinit var adapter: CacheAdapter

    private lateinit var dbManager: VideoDbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
        dbManager = VideoDbManager()
        Aria.download(this).register()
        ImmersionBar.with(activity).transparentBar().barAlpha(0.3F).fitsSystemWindows(true).init()
        initView()
        initData()
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.title = getString(R.string.mine_cache)
            bar.setDisplayHomeAsUpEnabled(true)
            tb.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CacheAdapter(context, beans, dbManager)
        rv.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val bean: VideoBean? = beans[position]
                bean?.let {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelable("data", bean)
                    intent.putExtra("bundle", bundle)
                    intent.putExtra("showCache", false)
                    context.startActivity(intent)
                }
            }

            override fun onItemLongClick(view: View, position: Int): Boolean {
                return false
            }
        })
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
            adapter.notifyDataSetChanged()
        }
    }

    @Download.onTaskRunning
    fun onDownloadProgress(task: DownloadTask) {
        if (adapter.beans != null && adapter.beans!!.size > 0) {
            for (index in adapter.beans!!.indices) {
                val bean = adapter.beans!![index]
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
        if (adapter.beans != null && adapter.beans!!.size > 0) {
            for (index in adapter.beans!!.indices) {
                val bean = adapter.beans!![index]
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
        if (adapter.beans != null && adapter.beans!!.size > 0) {
            for (index in adapter.beans!!.indices) {
                val bean = adapter.beans!![index]
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