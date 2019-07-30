package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.ImageUtil

class RankingAdapter(context: Context?, beans: ArrayList<HotBean.Item.Data>) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: ArrayList<HotBean.Item.Data>? = null
    var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.beans = beans
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_ranking, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans?.get(position)

        bean?.let {
            val photo = bean.cover.feed
            ImageUtil.display(context!!, holder.iv, photo)
            val title = bean.title
            holder.tvTitle.text = title
            val category = bean.category
            val duration = bean.duration
            val minute = duration.div(60)
            val second = duration.minus((minute.times(60)).toLong())
            val realMinute: String
            val realSecond: String
            realMinute = if (minute < 10) {
                "0$minute"
            } else {
                minute.toString()
            }
            realSecond = if (second < 10) {
                "0$second"
            } else {
                second.toString()
            }
            val content = "$category / $realMinute'$realSecond''"
            holder.tvTime.text = content
            holder.itemView.setOnClickListener {
                val intent = Intent(context, VideoDetailActivity::class.java)
                val id = bean.id
                val desc = bean.description
                val playUrl = bean.playUrl
                val blurred = bean.cover.blurred
                val collect = bean.consumption.collectionCount
                val share = bean.consumption.shareCount
                val reply = bean.consumption.replyCount
                val time = System.currentTimeMillis()
                val videoBean = VideoBean(id, photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
                val bundle = Bundle()
                bundle.putParcelable("data", videoBean)
                intent.putExtra("bundle", bundle)
                context?.startActivity(intent)
            }
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var iv: AppCompatImageView = itemView.findViewById(R.id.iv) as AppCompatImageView
        var tvTitle: AppCompatTextView = itemView.findViewById(R.id.tv_title) as AppCompatTextView
        var tvTime: AppCompatTextView = itemView.findViewById(R.id.tv_time) as AppCompatTextView

        init {
            tvTitle.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        }

    }

}