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
import com.yuzhentao.ktvideo.interfaces.OnItemClickListener
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ResourcesUtil
import com.yuzhentao.ktvideo.util.ViewUtil
import java.text.SimpleDateFormat
import java.util.*

class WatchAdapter(context: Context, beans: ArrayList<VideoBean>) : RecyclerView.Adapter<WatchAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: ArrayList<VideoBean>? = null
    private var inflater: LayoutInflater? = null

    private var listener: OnItemClickListener? = null

    init {
        this.context = context
        this.beans = beans
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_watch, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val bean = beans?.get(position)
            bean?.let {

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
            val photoUrl: String? = bean.feed
            photoUrl?.let {
                ImageUtil.display(context!!, holder.iv, photoUrl)
            }
            val title: String? = bean.title
            holder.tvTop!!.text = title
            val category: String? = bean.category
            val duration = bean.duration
            val minute = duration?.div(60)
            val second = minute?.times(60)?.let {
                duration.minus(it)
            }
            val realMinute: String
            val realSecond: String
            realMinute = if (minute!! < 10) {
                "0$minute"
            } else {
                minute.toString()
            }
            realSecond = if (second!! < 10) {
                "0$second"
            } else {
                second.toString()
            }
            val releaseTime = bean.time
            val smf = SimpleDateFormat("MM-dd", Locale.getDefault())
            val date = smf.format(releaseTime)
            val content = "$category / $realMinute'$realSecond'' / $date"
            holder.tvBottom!!.text = content
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

        init {
            iv = itemView.findViewById(R.id.iv) as AppCompatImageView
            tvTop = itemView.findViewById(R.id.tv_top) as AppCompatTextView
            tvBottom = itemView.findViewById(R.id.tv_bottom) as AppCompatTextView
            tvTop!!.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}