package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.ui.activity.VideoDetailActivity
import com.yuzhentao.ktvideo.util.ImageLoadUtils

class HomeAdapter(context: Context?, beans: MutableList<HomeBean.IssueListBean.ItemListBean>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var context: Context? = null
    var beans: MutableList<HomeBean.IssueListBean.ItemListBean>? = null
    var inflater: LayoutInflater? = null

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
        ImageLoadUtils.display(context!!, holder.ivPhoto, photo as String)
        if (icon == null) {
            holder.ivUser?.visibility = View.GONE
        } else {
            ImageLoadUtils.display(context!!, holder.ivUser, icon)
        }

        val title = bean.data?.title
        val category = bean.data?.category
        val minute = bean.data?.duration?.div(60)
        val second = bean.data?.duration?.minus((minute?.times(60)) as Long)
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
        val content = "发布于 $category / $realMinute:$realSecond"
        holder.tvTitle?.text = title
        holder.tvContent?.text = content

        holder.itemView.setOnClickListener {
            val intent = Intent(context, VideoDetailActivity::class.java)
            val desc = bean.data?.description
            val duration = bean.data?.duration
            val playUrl = bean.data?.playUrl
            val blurred = bean.data?.cover?.blurred
            val collect = bean.data?.consumption?.collectionCount
            val share = bean.data?.consumption?.shareCount
            val reply = bean.data?.consumption?.replyCount
            val time = System.currentTimeMillis()
            //todo
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