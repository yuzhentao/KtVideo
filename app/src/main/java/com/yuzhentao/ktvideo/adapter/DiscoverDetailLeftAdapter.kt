package com.yuzhentao.ktvideo.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.util.ImageUtil

class DiscoverDetailLeftAdapter(context: Context?, beans: MutableList<DiscoverDetailLeftBean.Item.Data>?) : RecyclerView.Adapter<DiscoverDetailLeftAdapter.ViewHolder>() {

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
        return ViewHolder(inflater!!.inflate(R.layout.item_discover_detail, parent, false), context!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = beans?.get(position)
        bean?.let {
            bean.content?.data?.author?.icon?.let {
                ImageUtil.showCircle(context!!, holder.ivIcon, bean.content.data.author.icon)
            }
            bean.content?.data?.author?.name?.let {
                holder.tvName!!.text = bean.content.data.author.name
            }
            bean.content?.data?.title?.let {
                val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                val spannableString = SpannableString(context!!.getString(R.string.discover_release, bean.content.data.title))
                val colorSpan = ForegroundColorSpan(ContextCompat.getColor(context!!, R.color.black))
                spannableString.setSpan(colorSpan, context!!.getString(R.string.discover_release, bean.content.data.title).indexOf(" "), context!!.getString(R.string.discover_release, bean.content.data.title).length, flags)
                val styleSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(styleSpan, context!!.getString(R.string.discover_release, bean.content.data.title).indexOf(" "), context!!.getString(R.string.discover_release, bean.content.data.title).length, flags)
                holder.tvTitle!!.text = spannableString
            }
            bean.content?.data?.description?.let {
                holder.tvDesc!!.text = bean.content.data.description
            }
            bean.content?.data?.tags?.get(0)?.name?.let {
                holder.tvTag1!!.text = bean.content.data.tags[0].name
                holder.tvTag1!!.visibility = View.VISIBLE
            }
            bean.content?.data?.tags?.get(1)?.name?.let {
                holder.tvTag2!!.text = bean.content.data.tags[1].name
                holder.tvTag2!!.visibility = View.VISIBLE
            }
            bean.content?.data?.tags?.get(2)?.name?.let {
                holder.tvTag3!!.text = bean.content.data.tags[2].name
                holder.tvTag3!!.visibility = View.VISIBLE
            }
            if (bean.content?.data?.consumption?.collectionCount == null) {
                holder.tvFavorite!!.text = "0"
            } else {
                holder.tvFavorite!!.text = bean.content.data.consumption.collectionCount.toString()
            }
            if (bean.content?.data?.consumption?.replyCount == null) {
                holder.tvReply!!.text = "0"
            } else {
                holder.tvReply!!.text = bean.content.data.consumption.replyCount.toString()
            }
            if (position == itemCount - 1) {
                holder.vLine!!.visibility = View.GONE
            }
            bean.content?.data?.playUrl?.let {
                val vp = holder.vp!!
                val ivCover = ImageView(context)
                ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                ImageUtil.show(context!!, ivCover, bean.content.data.cover.feed)
                vp.thumbImageView = ivCover
                vp.setUp(bean.content.data.playUrl, false, null, null)
                vp.titleTextView.visibility = View.GONE
                vp.backButton.visibility = View.GONE
                vp.playPosition = position
                vp.setIsTouchWiget(false)
            }
        }
    }

    class ViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView) {

        var ivIcon: AppCompatImageView? = null
        var tvName: AppCompatTextView? = null
        var tvTitle: AppCompatTextView? = null
        var tvDesc: AppCompatTextView? = null
        var tvTag1: AppCompatTextView? = null
        var tvTag2: AppCompatTextView? = null
        var tvTag3: AppCompatTextView? = null
        var vp: StandardGSYVideoPlayer? = null
        var tvFavorite: AppCompatTextView? = null
        var tvReply: AppCompatTextView? = null
        var tvPlayTime: AppCompatTextView? = null
        var vLine: View? = null

        init {
            ivIcon = itemView.findViewById(R.id.iv_icon) as AppCompatImageView
            tvName = itemView.findViewById(R.id.tv_name) as AppCompatTextView
            tvTitle = itemView.findViewById(R.id.tv_title) as AppCompatTextView
            tvDesc = itemView.findViewById(R.id.tv_desc) as AppCompatTextView
            tvTag1 = itemView.findViewById(R.id.tv_tag_1) as AppCompatTextView
            tvTag2 = itemView.findViewById(R.id.tv_tag_2) as AppCompatTextView
            tvTag3 = itemView.findViewById(R.id.tv_tag_3) as AppCompatTextView
            vp = itemView.findViewById(R.id.vp) as StandardGSYVideoPlayer
            tvFavorite = itemView.findViewById(R.id.tv_favorite) as AppCompatTextView
            tvReply = itemView.findViewById(R.id.tv_replay) as AppCompatTextView
            tvPlayTime = itemView.findViewById(R.id.tv_play_time) as AppCompatTextView
            vLine = itemView.findViewById(R.id.v_line)
        }

    }

}