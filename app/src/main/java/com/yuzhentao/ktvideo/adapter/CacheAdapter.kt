package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arialyy.aria.core.Aria
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.interfaces.OnItemClickListener
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.*
import com.yuzhentao.ktvideo.view.progressbutton.ProgressFloatingActionButton
import timber.log.Timber

class CacheAdapter(context: Context, beans: ArrayList<VideoBean>, dbManager: VideoDbManager) : RecyclerView.Adapter<CacheAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: ArrayList<VideoBean>? = null
    private var inflater: LayoutInflater? = null

    private var dbBean: VideoBean? = null
    var dbManager: VideoDbManager
    private var listener: OnItemClickListener? = null

    init {
        this.context = context
        this.beans = beans
        this.dbManager = dbManager
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_cache, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val bean = beans?.get(position)
            bean?.let {
                val btnProgress = holder.btnProgress
                if (bean.playUrl != null) {
                    dbBean = dbManager.find(bean.playUrl!!)
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
                if (bean.downloadProgress!! == 100) {
                    btnProgress!!.setProgressColor(ContextCompat.getColor(context!!, R.color.transparent))
                }
                btnProgress!!.setCurrentProgress(bean.downloadProgress!!, true)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans?.get(position)
        bean?.let {
            if (position != itemCount - 1) {
                ViewUtil.setMargins(holder.itemView, 0, 0, 0, ResourcesUtil.getDimensionPixelOffset(context!!, R.dimen.x2))
            } else {
                ViewUtil.setMargins(holder.itemView, 0, 0, 0, 0)
            }
            val btnProgress = holder.btnProgress
            val photoUrl: String? = bean.feed
            photoUrl?.let {
                ImageUtil.display(context!!, holder.iv, photoUrl)
            }
            val title: String? = bean.title
            holder.tvTop!!.text = title
            if (bean.playUrl != null) {
                dbBean = dbManager.find(bean.playUrl!!)
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
            if (bean.downloadProgress!! == 100) {
                btnProgress!!.setProgressColor(ContextCompat.getColor(context!!, R.color.transparent))
            }
            btnProgress!!.setCurrentProgress(bean.downloadProgress!!, true)
            btnProgress.setOnClickListener {
                dbBean = dbManager.find(bean.playUrl!!)
                dbBean?.let {
                    when (dbBean!!.downloadState) {
                        DownloadState.DOWNLOADING.name -> {
                            Timber.tag("缓存").e("暂停>>>${bean.title}")
                            Aria.download(this).load(bean.playUrl!!).stop()
                            bean.downloadState = DownloadState.PAUSE.name
                            dbManager.update(bean)
                            notifyItemChanged(position, 1)
                        }
                        DownloadState.PAUSE.name -> {
                            Timber.tag("缓存").e("恢复>>>${bean.title}")
                            Aria.download(this).load(bean.playUrl!!).resume()
                            bean.downloadState = DownloadState.DOWNLOADING.name
                            dbManager.update(bean)
                            notifyItemChanged(position, 1)
                        }
                        DownloadState.COMPLETE.name -> {
                            val intent = Intent(context, VideoDetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("data", bean)
                            intent.putExtra("bundle", bundle)
                            intent.putExtra("showCache", false)
                            intent.putExtra("autoPlay", true)
                            context!!.startActivity(intent)
                        }
                        else -> {
                            context!!.shortToast(context!!.getString(R.string.cache_fail))
                        }
                    }
                }
            }
            listener?.let {
                holder.itemView.setOnClickListener {
                    listener!!.onItemClick(holder.itemView, holder.layoutPosition)
                }
                holder.itemView.setOnLongClickListener {
                    listener!!.onItemLongClick(holder.itemView, holder.layoutPosition)
                    true
                }
            }
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var iv: AppCompatImageView? = null
        var tvTop: AppCompatTextView? = null
        var btnProgress: ProgressFloatingActionButton? = null

        init {
            iv = itemView.findViewById(R.id.iv) as AppCompatImageView
            tvTop = itemView.findViewById(R.id.tv_top) as AppCompatTextView
            btnProgress = itemView.findViewById(R.id.btn_progress) as ProgressFloatingActionButton
            tvTop!!.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}