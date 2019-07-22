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
import com.yuzhentao.ktvideo.bean.HotBean

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

    override fun onBindViewHolder(parent: ViewHolder, viewType: Int) {

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