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
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ObjectSaveUtils
import com.yuzhentao.ktvideo.util.SPUtils

class HomeAdapter(context: Context?, beans: MutableList<HomeBean.Issue.Item>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var context: Context? = null
    private var beans: MutableList<HomeBean.Issue.Item>? = null
    private var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.beans = beans
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0//字段后加!!像Java一样抛出空异常，字段后加?可不做处理返回值为null或配合?:做空判断处理
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_home, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans?.get(position)

        val photo = bean?.data?.cover?.feed
        val icon = bean?.data?.author?.icon
        ImageUtil.display(context!!, holder.ivPhoto, photo as String)
        if (icon == null) {
            holder.ivUser?.visibility = View.GONE
        } else {
            ImageUtil.display(context!!, holder.ivUser, icon)
        }

        val title = bean.data.title
        val category = bean.data.category
        val duration = bean.data.duration
        val minute = duration.div(60)
        val second = duration.minus((minute.times(60)))
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
        val content = "发布于 $category / $realMinute:$realSecond"
        holder.tvTitle?.text = title
        holder.tvContent?.text = content

        holder.itemView.setOnClickListener {
            val intent = Intent(context, VideoDetailActivity::class.java)
            val desc = bean.data.description
            val playUrl = bean.data.playUrl
            val blurred = bean.data.cover.blurred
            val collect = bean.data.consumption.collectionCount
            val share = bean.data.consumption.shareCount
            val reply = bean.data.consumption.replyCount
            val time = System.currentTimeMillis()
            val videoBean = VideoBean(photo, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
            val url = SPUtils.getInstance(context!!, "beans").getString(playUrl)//是否保存视频地址
            if (url == "") {
                var count = SPUtils.getInstance(context!!, "beans").getInt("count")
                count = if (count != -1) {
                    count.inc()//i++
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

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var ivPhoto: AppCompatImageView? = null
        var ivUser: AppCompatImageView? = null
        var tvTitle: AppCompatTextView? = null
        var tvContent: AppCompatTextView? = null

        init {
            ivPhoto = itemView.findViewById(R.id.iv_photo) as AppCompatImageView
            ivUser = itemView.findViewById(R.id.iv_user) as AppCompatImageView
            tvTitle = itemView.findViewById(R.id.tv_title) as AppCompatTextView
            tvContent = itemView.findViewById(R.id.tv_content) as AppCompatTextView
            tvTitle?.typeface = Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        }

    }

}