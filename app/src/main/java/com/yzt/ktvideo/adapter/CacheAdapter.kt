package com.yzt.ktvideo.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.alibaba.android.arouter.launcher.ARouter
import com.arialyy.aria.core.Aria
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.bean.DownloadState
import com.yzt.bean.VideoBean
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.extension.shortToast
import com.yzt.common.key.Constant
import com.yzt.common.util.ImageUtil
import com.yzt.common.util.ViewUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.view.progressbutton.ProgressButton
import timber.log.Timber

/**
 * 我的缓存
 *
 * @author yzt 2021/2/9
 */
class CacheAdapter(data: MutableList<VideoBean>?, private var dbManager: VideoDbManager) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_cache, data) {

    init {
        addChildClickViewIds(R.id.tv_delete)
    }

    private var dbBean: VideoBean? = null

    override fun convert(holder: BaseViewHolder, item: VideoBean) {
        val position = holder.layoutPosition
        if (position != itemCount - 1) {
            ViewUtil.setMargins(
                holder.itemView,
                0,
                0,
                0,
                context.dimensionPixelOffset(R.dimen.dp_2)
            )
        } else {
            ViewUtil.setMargins(holder.itemView, 0, 0, 0, 0)
        }
        val btnProgress = holder.getView<ProgressButton>(R.id.btn_progress)
        val img: String? = item.feed
        img?.let {
            ImageUtil.show(context, holder.getView<AppCompatImageView>(R.id.iv), it)
        }
        val title: String? = item.title
        holder.getView<AppCompatTextView>(R.id.tv_top).text = title
        if (item.playUrl != null) {
            dbBean = dbManager.find(item.playUrl!!)
            dbBean?.let {
                when (it.downloadState) {
                    DownloadState.DOWNLOADING.name -> {
                        btnProgress.setImageRes(R.drawable.selector_pause)
                    }
                    DownloadState.PAUSE.name -> {
                        btnProgress.setImageRes(R.drawable.selector_play)
                    }
                    DownloadState.COMPLETE.name -> {
                        btnProgress.setImageRes(R.drawable.selector_play)
                    }
                    else -> {
                        btnProgress.setImageRes(R.drawable.selector_error)
                    }
                }
            }
        } else {
            btnProgress.setImageRes(R.drawable.selector_error)
        }
        if (item.downloadProgress!! == 100) {
            btnProgress.setProgressColor(context.color(R.color.transparent))
        }
        btnProgress.setCurrentProgress(item.downloadProgress!!, true)
        btnProgress.setOnClickListener {
            dbBean = dbManager.find(item.playUrl!!)
            dbBean?.let {
                when (it.downloadState) {
                    DownloadState.DOWNLOADING.name -> {
                        Timber.tag("缓存").e("暂停>>>${item.title}")
                        Aria.download(this).load(item.downloadId!!).stop()
                        item.downloadState = DownloadState.PAUSE.name
                        dbManager.update(item)
                        notifyItemChanged(position)
                    }
                    DownloadState.PAUSE.name -> {
                        Timber.tag("缓存").e("恢复>>>${item.title}")
                        Aria.download(this).load(item.downloadId!!).resume()
                        item.downloadState = DownloadState.DOWNLOADING.name
                        dbManager.update(item)
                        notifyItemChanged(position)
                    }
                    DownloadState.COMPLETE.name -> {
                        ARouter
                            .getInstance()
                            .build(Constant.PATH_VIDEO_DETAIL)
                            .withParcelable("bean", item)
                            .withBoolean("showCache", false)
                            .withBoolean("autoPlay", true)
                            .navigation()
                    }
                    else -> {
                        context.shortToast(context.getString(R.string.cache_fail))
                    }
                }
            }
        }
    }

}