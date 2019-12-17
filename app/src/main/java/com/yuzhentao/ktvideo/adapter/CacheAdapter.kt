package com.yuzhentao.ktvideo.adapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.arialyy.aria.core.Aria
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.extension.color
import com.yuzhentao.ktvideo.extension.dimensionPixelOffset
import com.yuzhentao.ktvideo.extension.shortToast
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.DownloadState
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ViewUtil
import com.yuzhentao.ktvideo.view.progressbutton.ProgressButton
import timber.log.Timber

class CacheAdapter(data: MutableList<VideoBean>?, private var dbManager: VideoDbManager) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_cache, data) {

    private var dbBean: VideoBean? = null

    override fun convert(helper: BaseViewHolder, item: VideoBean?) {
        item?.let {
            val position = helper.layoutPosition
            if (position != itemCount - 1) {
                ViewUtil.setMargins(
                    helper.itemView,
                    0,
                    0,
                    0,
                    mContext!!.dimensionPixelOffset(R.dimen.x2)
                )
            } else {
                ViewUtil.setMargins(helper.itemView, 0, 0, 0, 0)
            }
            val btnProgress = helper.getView<ProgressButton>(R.id.btn_progress)
            val img: String? = item.feed
            img?.let {
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv), img)
            }
            val title: String? = item.title
            helper.getView<AppCompatTextView>(R.id.tv_top)!!.text = title
            if (item.playUrl != null) {
                dbBean = dbManager.find(item.playUrl!!)
                dbBean?.let {
                    when (dbBean!!.downloadState) {
                        DownloadState.DOWNLOADING.name -> {
                            btnProgress!!.setImageRes(R.drawable.selector_pause)
                        }
                        DownloadState.PAUSE.name -> {
                            btnProgress!!.setImageRes(R.drawable.selector_play)
                        }
                        DownloadState.COMPLETE.name -> {
                            btnProgress!!.setImageRes(R.drawable.selector_play)
                        }
                        else -> {
                            btnProgress!!.setImageRes(R.drawable.selector_error)
                        }
                    }
                }
            } else {
                btnProgress!!.setImageRes(R.drawable.selector_error)
            }
            if (item.downloadProgress!! == 100) {
                btnProgress!!.setProgressColor(mContext!!.color(R.color.transparent))
            }
            btnProgress!!.setCurrentProgress(item.downloadProgress!!, true)
            btnProgress.setOnClickListener {
                dbBean = dbManager.find(item.playUrl!!)
                dbBean?.let {
                    when (dbBean!!.downloadState) {
                        DownloadState.DOWNLOADING.name -> {
                            Timber.tag("缓存").e("暂停>>>${item.title}")
                            Aria.download(this).load(item.playUrl!!).stop()
                            item.downloadState = DownloadState.PAUSE.name
                            dbManager.update(item)
                            notifyItemChanged(position, 1)
                        }
                        DownloadState.PAUSE.name -> {
                            Timber.tag("缓存").e("恢复>>>${item.title}")
                            Aria.download(this).load(item.playUrl!!).resume()
                            item.downloadState = DownloadState.DOWNLOADING.name
                            dbManager.update(item)
                            notifyItemChanged(position, 1)
                        }
                        DownloadState.COMPLETE.name -> {
                            val intent = Intent(mContext, VideoDetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("data", item)
                            intent.putExtra("bundle", bundle)
                            intent.putExtra("showCache", false)
                            intent.putExtra("autoPlay", true)
                            mContext!!.startActivity(intent)
                        }
                        else -> {
                            mContext!!.shortToast(mContext!!.getString(R.string.cache_fail))
                        }
                    }
                }
            }
            helper.addOnClickListener(R.id.tv_delete)
        }
    }

}