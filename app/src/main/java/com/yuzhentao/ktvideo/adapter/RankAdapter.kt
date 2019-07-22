package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Parcelable
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
import com.yuzhentao.ktvideo.util.ObjectSaveUtils
import com.yuzhentao.ktvideo.util.SPUtils

class RankAdapter(context: Context?, beans: ArrayList<HotBean.Item.Data>) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

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
        return ViewHolder(inflater!!.inflate(R.layout.item_rank, parent, false), context!!)
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
                val desc = bean.description
                val playUrl = bean.playUrl
                val blurred = bean.cover.blurred
                val collect = bean.consumption.collectionCount
                val share = bean.consumption.shareCount
                val reply = bean.consumption.replyCount
                val time = System.currentTimeMillis()
                val videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
                val url = SPUtils.getInstance(context!!, "beans").getString(playUrl)//是否保存视频地址
                if (url == "") {
                    var count = SPUtils.getInstance(context!!, "beans").getInt("count")
                    count = if (count != -1) {
                        count.inc()
                    } else {
                        1
                    }
                    SPUtils.getInstance(context!!, "beans").put(playUrl, playUrl)//视频地址
                    SPUtils.getInstance(context!!, "beans").put("count", count)//保存的视频对象数量
                    ObjectSaveUtils.saveObject(context!!, "bean$count", videoBean)//保存视频对象，观看记录中会使用到
                }
                intent.putExtra("data", videoBean as Parcelable)
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