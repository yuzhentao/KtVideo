package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.graphics.Typeface
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
import com.yuzhentao.ktvideo.util.DownloadState
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ResourcesUtil
import com.yuzhentao.ktvideo.util.ViewUtil
import io.reactivex.disposables.Disposable

class CacheAdapter(context: Context, beans: ArrayList<VideoBean>, dbManager: VideoDbManager) : RecyclerView.Adapter<CacheAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: ArrayList<VideoBean>? = null
    var dbManager: VideoDbManager
    private var inflater: LayoutInflater? = null
    private var dbBean: VideoBean? = null
    var hasLoaded = false
    lateinit var disposable: Disposable

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
                holder.tvProgress!!.text = bean.downloadProgress.toString()
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
            val ivDownload = holder.ivDownload
            val photoUrl: String? = bean.feed
            photoUrl?.let {
                dbBean = dbManager.find(bean.playUrl!!)
                ImageUtil.display(context!!, holder.iv, photoUrl)
            }
            val title: String? = bean.title
            holder.tvTop!!.text = title
            if (bean.playUrl != null) {
                dbBean?.let {
                    if (dbBean!!.downloadState == DownloadState.DOWNLOADING.name) {
                        ivDownload!!.setImageResource(R.drawable.selector_pause)
                    } else if (dbBean!!.downloadState == DownloadState.COMPLETE.name) {
                        ivDownload!!.setImageResource(R.drawable.selector_play)
                    } else {
                        ivDownload!!.setImageResource(R.drawable.selector_error)
                    }
                }
            } else {
                ivDownload!!.setImageResource(R.drawable.selector_error)
            }
            ivDownload!!.setOnClickListener {
                dbBean?.let {
                    when (dbBean!!.downloadState) {
                        DownloadState.DOWNLOADING.name -> {
                            Aria.download(this).load(bean.playUrl!!).stop()
                            bean.downloadState = DownloadState.PAUSE.name
                            dbManager.update(bean)
                        }
                        DownloadState.PAUSE.name -> {
                            Aria.download(this).load(bean.playUrl!!).start()
                            bean.downloadState = DownloadState.DOWNLOADING.name
                            dbManager.update(bean)
                        }
                        DownloadState.COMPLETE.name -> {
                            listener!!.onItemClick(holder.itemView, holder.layoutPosition)
                        }
                        DownloadState.ERROR.name -> {
                            listener!!.onItemClick(holder.itemView, holder.layoutPosition)
                        }
                    }
                }
            }
            holder.tvProgress!!.text = bean.downloadProgress.toString()
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
        var tvBottom: AppCompatTextView? = null
        var ivDownload: AppCompatImageView? = null
        var tvProgress: AppCompatTextView? = null

        init {
            iv = itemView.findViewById(R.id.iv) as AppCompatImageView
            tvTop = itemView.findViewById(R.id.tv_top) as AppCompatTextView
            tvBottom = itemView.findViewById(R.id.tv_bottom) as AppCompatTextView
            ivDownload = itemView.findViewById(R.id.iv_download) as AppCompatImageView
            tvProgress = itemView.findViewById(R.id.tv_progress) as AppCompatTextView
            tvTop!!.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}