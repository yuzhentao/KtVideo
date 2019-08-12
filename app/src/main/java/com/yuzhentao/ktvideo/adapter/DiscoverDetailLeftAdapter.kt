package com.yuzhentao.ktvideo.adapter

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.util.ImageUtil

class DiscoverDetailLeftAdapter(layoutResId: Int, data: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?) : BaseQuickAdapter<DiscoverDetailLeftBean.Item.Data.Content, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: DiscoverDetailLeftBean.Item.Data.Content?) {
        helper?.let {
            val position = helper.layoutPosition
            item?.let {
                val ivIcon = helper.getView(R.id.iv_icon) as AppCompatImageView
                val tvName = helper.getView(R.id.tv_name) as AppCompatTextView
                val tvTitle = helper.getView(R.id.tv_title) as AppCompatTextView
                val tvDesc = helper.getView(R.id.tv_desc) as AppCompatTextView
                val tvTag1 = helper.getView(R.id.tv_tag_1) as AppCompatTextView
                val tvTag2 = helper.getView(R.id.tv_tag_2) as AppCompatTextView
                val tvTag3 = helper.getView(R.id.tv_tag_3) as AppCompatTextView
                val tvFavorite = helper.getView(R.id.tv_favorite) as AppCompatTextView
                val tvReply = helper.getView(R.id.tv_replay) as AppCompatTextView
                val vLine = helper.getView(R.id.v_line) as View
                val vp = helper.getView(R.id.vp) as StandardGSYVideoPlayer
                item.data?.author?.icon?.let {
                    ImageUtil.showCircle(mContext, ivIcon, item.data.author.icon)
                }
                item.data?.author?.name?.let {
                    tvName.text = item.data.author.name
                }
                item.data?.title?.let {
                    val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    val spannableString = SpannableString(mContext.getString(R.string.discover_release, item.data.title))
                    val colorSpan = ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.black))
                    spannableString.setSpan(colorSpan, mContext.getString(R.string.discover_release, item.data.title).indexOf(" "), mContext.getString(R.string.discover_release, item.data.title).length, flags)
                    val styleSpan = StyleSpan(Typeface.BOLD)
                    spannableString.setSpan(styleSpan, mContext.getString(R.string.discover_release, item.data.title).indexOf(" "), mContext.getString(R.string.discover_release, item.data.title).length, flags)
                    tvTitle.text = spannableString
                }
                item.data?.description?.let {
                    tvDesc.text = item.data.description
                }
                item.data?.tags?.get(0)?.name?.let {
                    tvTag1.text = item.data.tags[0].name
                    tvTag1.visibility = View.VISIBLE
                }
                item.data?.tags?.get(1)?.name?.let {
                    tvTag2.text = item.data.tags[1].name
                    tvTag2.visibility = View.VISIBLE
                }
                item.data?.tags?.get(2)?.name?.let {
                    tvTag3.text = item.data.tags[2].name
                    tvTag3.visibility = View.VISIBLE
                }
                if (item.data?.consumption?.collectionCount == null) {
                    tvFavorite.text = "0"
                } else {
                    tvFavorite.text = item.data.consumption.collectionCount.toString()
                }
                if (item.data?.consumption?.replyCount == null) {
                    tvReply.text = "0"
                } else {
                    tvReply.text = item.data.consumption.replyCount.toString()
                }
                if (position == itemCount - 1) {
                    vLine.visibility = View.GONE
                }
                item.data?.playUrl?.let {
                    val ivCover = ImageView(mContext)
                    ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                    ImageUtil.show(mContext, ivCover, item.data.cover.feed)
                    vp.thumbImageView = ivCover
                    vp.setUp(item.data.playUrl, false, null, null)
                    vp.titleTextView.visibility = View.GONE
                    vp.backButton.visibility = View.GONE
                    vp.playPosition = position
                    vp.setIsTouchWiget(false)
                }
            }
        }
    }

}