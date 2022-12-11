package com.yzt.ktvideo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.bean.DownloadState
import com.yzt.bean.VideoBean
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.extension.shortToast
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.FileUtil
import com.yzt.common.util.FooterUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.CacheAdapter
import com.yzt.ktvideo.databinding.ActivityCacheBinding
import com.yzt.ktvideo.view.EasySwipeMenuLayout
import timber.log.Timber

/**
 * 我的缓存
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_CACHE)
class CacheActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var binding: ActivityCacheBinding? = null

    private var beans: MutableList<VideoBean> = mutableListOf()
    private val adapter: CacheAdapter by lazy {
        CacheAdapter(null, dbManager)
    }

    private val dbManager: VideoDbManager by lazy {
        VideoDbManager()
    }

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityCacheBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        Aria.download(this).register()
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
        binding!!.tvTop.text = getString(R.string.mine_cache)
        binding!!.rv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        (binding!!.rv.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        binding!!.rv.adapter = adapter
        adapter.setOnItemClickListener { adapter, _, position ->
            val bean: VideoBean? = adapter.data[position] as VideoBean?
            bean?.let {
                ARouter
                    .getInstance()
                    .build(Constant.PATH_VIDEO_DETAIL)
                    .withParcelable("bean", it)
                    .withBoolean("showCache", false)
                    .navigation()
            }
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val bean: VideoBean? = adapter.data[position] as VideoBean?
            bean?.let {
                when (view.id) {
                    R.id.tv_delete -> {
                        val builder = AlertDialog.Builder(context!!)
                        builder.setMessage(context!!.resources.getString(R.string.delete_tip))
                        builder.setPositiveButton(context!!.resources.getString(R.string.yes)) { dialog, _ ->
                            dialog.dismiss()
                            if (FileUtil.deleteFile(it.savePath)) {
                                adapter.removeAt(position)
                                it.downloadState = DownloadState.NORMAL.name
                                it.downloadProgress = 0
                                it.savePath = ""
                                dbManager.update(it)
                            }
                            if (adapter.data.size == 0) {
                                binding!!.rv.visibility = View.GONE
                                binding!!.tvHint.visibility = View.VISIBLE
                            }
                        }
                        builder.setNegativeButton(context!!.resources.getString(R.string.no)) { dialog, _ ->
                            dialog.dismiss()
                            val swipe = adapter.getViewByPosition(
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
        adapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
    }

    override fun initData(savedInstanceState: Bundle?) {
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
                binding!!.rv.visibility = View.VISIBLE
                binding!!.tvHint.visibility = View.GONE
            } else {
                binding!!.rv.visibility = View.GONE
                binding!!.tvHint.visibility = View.VISIBLE
            }
            adapter.setList(beans)
            adapter.addFooterView(FooterUtil.getFooter(context!!, color(R.color.app_black)))
        }
    }

    override fun initLifecycleObserver() {

    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
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
                    adapter.notifyItemChanged(index)
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
                    adapter.notifyItemChanged(index)
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
                    adapter.notifyItemChanged(index)
                }
            }
        }
    }

}