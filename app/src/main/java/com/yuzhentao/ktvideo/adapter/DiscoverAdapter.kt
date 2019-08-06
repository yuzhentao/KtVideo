package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.ui.activity.DiscoverDetailActivity
import com.yuzhentao.ktvideo.util.ImageUtil

class DiscoverAdapter(context: Context?, beans: MutableList<DiscoverBean>?) : RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {

    var context: Context? = null
    private var beans: MutableList<DiscoverBean>? = null
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
        return ViewHolder(inflater!!.inflate(R.layout.item_discover, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans?.get(position)

        ImageUtil.display(context!!, holder.ivPhoto, bean!!.bgPicture)
        holder.tv?.text = bean.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DiscoverDetailActivity::class.java)
            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View, context: Context?) : RecyclerView.ViewHolder(itemView) {

        var ivPhoto: AppCompatImageView? = null
        var ivMask: AppCompatImageView? = null
        var tv: AppCompatTextView? = null

        init {
            ivPhoto = itemView.findViewById(R.id.iv_photo) as AppCompatImageView
            ivMask = itemView.findViewById(R.id.iv_mask) as AppCompatImageView
            tv = itemView.findViewById(R.id.tv) as AppCompatTextView
        }

    }

}