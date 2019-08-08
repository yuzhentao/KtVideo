package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean

class DiscoverLeftAdapter(context: Context?, beans: MutableList<DiscoverDetailLeftBean.Item.Data>?) : RecyclerView.Adapter<DiscoverLeftAdapter.ViewHolder>() {

    private var context: Context? = null
    private var beans: MutableList<DiscoverDetailLeftBean.Item.Data>? = null
    private var inflater: LayoutInflater? = null

    init {
        this.context = context
        this.beans = beans
        this.inflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return beans?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater!!.inflate(R.layout.item_discover_detail_left, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

    }

}