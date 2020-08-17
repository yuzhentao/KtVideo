package com.yzt.ktvideo.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.download.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.CacheAdapter
import com.yzt.ktvideo.bean.VideoBean
import com.yzt.ktvideo.db.VideoDbManager
import com.yzt.ktvideo.extension.color
import com.yzt.ktvideo.extension.shortToast
import com.yzt.ktvideo.util.ClickUtil
import com.yzt.ktvideo.util.DownloadState
import com.yzt.ktvideo.util.FileUtil
import com.yzt.ktvideo.util.FooterUtil
import com.yzt.ktvideo.view.EasySwipeMenuLayout
import kotlinx.android.synthetic.main.activity_cache.*
import timber.log.Timber

/**
 * 我的缓存
 */
class CacheActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this

    private var beans: MutableList<VideoBean> = mutableListOf()
    private val adapter: CacheAdapter by lazy {
        CacheAdapter(null, dbManager)
    }

    private val dbManager: VideoDbManager by lazy {
        VideoDbManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_cache)
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
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressed()
                }
            }
        }
    }

    private fun initView() {
        tv_top.text = getString(R.string.mine_cache)
        iv_top.setOnClickListener(this)
        rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
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
        adapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val bean: VideoBean? = adapter!!.data[position] as VideoBean
                bean?.let {
                    when (view.id) {
                        R.id.tv_delete -> {
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage(context.resources.getString(R.string.delete_tip))
                            builder.setPositiveButton(context.resources.getString(R.string.yes)) { dialog, _ ->
                                dialog.dismiss()
                                if (FileUtil.deleteFile(bean.savePath)) {
                                    adapter.remove(position)
                                    bean.downloadState = DownloadState.NORMAL.name
                                    bean.downloadProgress = 0
                                    bean.savePath = ""
                                    dbManager.update(bean)
                                }
                                if (adapter.data.size == 0) {
                                    rv.visibility = View.GONE
                                    tv_hint.visibility = View.VISIBLE
                                }
                            }
                            builder.setNegativeButton(context.resources.getString(R.string.no)) { dialog, _ ->
                                dialog.dismiss()
                                val swipe = adapter.getViewByPosition(
                                    rv,
                                    position,
                                    R.id.swipe
                                ) as EasySwipeMenuLayout
                                swipe.resetStatus()
                            }
                            builder.create().show()
                        }
                    }
                }
            }
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
    }

    private fun initData() {
        dbManager.findCache()?.let {
            it.forEach { bean ->
                if ((FileUtil.isFileExist(bean.savePath)
                            && bean.downloadState == DownloadState.COMPLETE.name
                            && bean.downloadProgress == 100)
                    || (!FileUtil.isFileExist(bean.savePath)
                            && bean.downloadState != DownloadState.COMPLETE.name
                            && bean.downloadProgress != 100)
                ) {//检查本地是否还有视频
                    beans.add(bean)
                }
            }
            if (beans.size > 0) {
                rv.visibility = View.VISIBLE
                tv_hint.visibility = View.GONE
            } else {
                rv.visibility = View.GONE
                tv_hint.visibility = View.VISIBLE
            }
            adapter.setNewData(beans)
            adapter.addFooterView(FooterUtil.getFooter(context, context.color(R.color.app_black)))
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