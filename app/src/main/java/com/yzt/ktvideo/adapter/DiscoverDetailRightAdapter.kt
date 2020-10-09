package com.yzt.ktvideo.adapter

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yzt.ktvideo.R
import com.yzt.ktvideo.bean.DiscoverDetailRightBean
import com.yzt.ktvideo.extension.color
import com.yzt.ktvideo.extension.dimensionPixelOffset
import com.yzt.ktvideo.util.ImageUtil
import com.yzt.ktvideo.util.ViewUtil
import com.yzt.ktvideo.view.expandtextview.ExpandLayout

class DiscoverDetailRightAdapter(data: MutableList<DiscoverDetailRightBean.Item.Data.Content>?) :
    BaseQuickAdapter<DiscoverDetailRightBean.Item.Data.Content, BaseViewHolder>(
        R.layout.item_discover_detail,
        data
    ) {

    override fun convert(holder: BaseViewHolder, item: DiscoverDetailRightBean.Item.Data.Content) {
        val data = item.data
        data?.let {
            val position = holder.layoutPosition
            val ivIcon = holder.getView<AppCompatImageView>(R.id.iv_icon)
            val tvName = holder.getView<AppCompatTextView>(R.id.tv_name)
            val tvTitle = holder.getView<AppCompatTextView>(R.id.tv_title)
            val tvDesc = holder.getView<ExpandLayout>(R.id.tv_desc)
            val flexBox = holder.getView<FlexboxLayout>(R.id.flex_box)
            val tvFavorite = holder.getView<AppCompatTextView>(R.id.tv_favorite)
            val tvReply = holder.getView<AppCompatTextView>(R.id.tv_replay)
            val tvPlayTime = holder.getView<AppCompatTextView>(R.id.tv_play_time)
            val vLine = holder.getView<View>(R.id.v_line)
            val vp = holder.getView<StandardGSYVideoPlayer>(R.id.vp)
            data.owner?.avatar?.let {
                ImageUtil.showCircle(context, ivIcon, data.owner.avatar)
            }
            data.owner?.nickname?.let {
                tvName.text = item.data.owner!!.nickname
            }
            data.title?.let {
                val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                val spannableString =
                    SpannableString(context.getString(R.string.discover_release, item.data.title))
                val colorSpan = ForegroundColorSpan(context.color(R.color.app_black))
                spannableString.setSpan(
                    colorSpan,
                    context.getString(R.string.discover_release, item.data.title).indexOf(" "),
                    context.getString(R.string.discover_release, item.data.title).length,
                    flags
                )
                val styleSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(
                    styleSpan,
                    context.getString(R.string.discover_release, item.data.title).indexOf(" "),
                    context.getString(R.string.discover_release, item.data.title).length,
                    flags
                )
                tvTitle.text = spannableString
            }
            data.description?.let {
                tvDesc.setText(
                    item.data.description!!,
                    data.isExpand,
                    object : ExpandLayout.OnExpandListener {
                        override fun expandChange() {
                            if (!data.isExpand) {
                                data.isExpand = true
                                notifyItemChanged(position)
                            }
                        }
                    })
            }
            data.tags?.let {
                if (flexBox.childCount > 0) {
                    return
                }

                for (i in data.tags.indices) {
                    data.tags[i].name?.let {
                        val tv = AppCompatTextView(context)
                        tv.text = data.tags[i].name
                        tv.setTextColor(context.color(R.color.app_pink))
                        tv.setBackgroundResource(R.drawable.shape_tag)
                        tv.ellipsize = TextUtils.TruncateAt.END
                        tv.gravity = Gravity.CENTER
                        tv.includeFontPadding = false
                        tv.maxLines = 1
                        tv.textDirection = View.TEXT_DIRECTION_LOCALE
                        tv.visibility = View.VISIBLE
                        ViewUtil.setPaddings(
                            tv,
                            context.dimensionPixelOffset(R.dimen.x4),
                            context.dimensionPixelOffset(R.dimen.x2),
                            context.dimensionPixelOffset(R.dimen.x4),
                            context.dimensionPixelOffset(R.dimen.x2)
                        )
                        flexBox.addView(tv)
                    }
                }
            }
            if (data.consumption?.collectionCount == null) {
                tvFavorite.text = "0"
            } else {
                tvFavorite.text = item.data.consumption!!.collectionCount.toString()
            }
            if (data.consumption?.replyCount == null) {
                tvReply.text = "0"
            } else {
                tvReply.text = item.data.consumption!!.replyCount.toString()
            }
            if (position == itemCount - 1) {
                vLine.visibility = View.GONE
            }
            data.playUrl?.let {
                val ivCover = ImageView(context)
                ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                item.data.cover?.feed?.let {
                    ImageUtil.show(context, ivCover, item.data.cover.feed)
                }
                vp.thumbImageView = ivCover
                vp.setUp(item.data.playUrl, false, null, null)
                vp.titleTextView.visibility = View.GONE
                vp.backButton.visibility = View.GONE
                vp.playPosition = position
                vp.setIsTouchWiget(false)
                vp.setGSYVideoProgressListener { _, _, currentPosition, _ ->
                    run {
                        tvPlayTime.text = CommonUtil.stringForTime(currentPosition)
                    }
                }
            }
        }
    }

}