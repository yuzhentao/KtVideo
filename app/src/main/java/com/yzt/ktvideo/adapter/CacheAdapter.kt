package com.yzt.ktvideo.adapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.arialyy.aria.core.Aria
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.ktvideo.R
import com.yzt.common.bean.VideoBean
import com.yzt.common.db.VideoDbManager
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.extension.shortToast
import com.yzt.common.util.DownloadState
import com.yzt.ktvideo.ui.activity.VideoDetailActivity
import com.yzt.ktvideo.util.ImageUtil
import com.yzt.common.util.ViewUtil
import com.yzt.ktvideo.view.progressbutton.ProgressButton
import timber.log.Timber

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
                context.dimensionPixelOffset(R.dimen.x2)
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
                        notifyItemChanged(position, 1)
                    }
                    DownloadState.PAUSE.name -> {
                        Timber.tag("缓存").e("恢复>>>${item.title}")
                        Aria.download(this).load(item.downloadId!!).resume()
                        item.downloadState = DownloadState.DOWNLOADING.name
                        dbManager.update(item)
                        notifyItemChanged(position, 1)
                    }
                    DownloadState.COMPLETE.name -> {
                        val intent = Intent(context, VideoDetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putParcelable("data", item)
                        intent.putExtra("bundle", bundle)
                        intent.putExtra("showCache", false)
                        intent.putExtra("autoPlay", true)
                        context.startActivity(intent)
                    }
                    else -> {
                        context.shortToast(context.getString(R.string.cache_fail))
                    }
                }
            }
        }
    }

}