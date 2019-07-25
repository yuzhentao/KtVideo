package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ResourcesUtil
import com.yuzhentao.ktvideo.util.SPUtils
import com.yuzhentao.ktvideo.util.ViewUtil
import io.reactivex.disposables.Disposable

class CacheAdapter(context: Context, beans: ArrayList<VideoBean>) : RecyclerView.Adapter<CacheAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: ArrayList<VideoBean>? = null
    var inflater: LayoutInflater? = null
    var isDownload = false
    var hasLoaded = false
    lateinit var disposable: Disposable

    init {
        this.context = context
        this.beans = beans
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_cache, parent, false), context!!)
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
                ImageUtil.display(context!!, holder.iv, photoUrl)
            }
            val title: String? = bean.title
            holder.tvTop!!.text = title
            isDownload = SPUtils.getInstance(context!!, "download_state").getBoolean(bean.playUrl!!)
            if (isDownload) {
                ivDownload!!.setImageResource(R.drawable.selector_pause)
            } else {
                ivDownload!!.setImageResource(R.drawable.selector_play)
            }
            ivDownload.setOnClickListener {
                if (isDownload) {

                } else {

                }
            }
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var iv: AppCompatImageView? = null
        var tvTop: AppCompatTextView? = null
        var tvBottom: AppCompatTextView? = null
        var ivDownload: AppCompatImageView? = null

        init {
            iv = itemView.findViewById(R.id.iv) as AppCompatImageView
            tvTop = itemView.findViewById(R.id.tv_top) as AppCompatTextView
            tvBottom = itemView.findViewById(R.id.tv_bottom) as AppCompatTextView
            ivDownload = itemView.findViewById(R.id.iv_download) as AppCompatImageView
            tvTop!!.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }

    }

}