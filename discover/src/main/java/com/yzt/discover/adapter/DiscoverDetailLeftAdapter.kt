package com.yzt.discover.adapter

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
import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.util.ImageUtil
import com.yzt.common.util.ViewUtil
import com.yzt.common.view.expandtextview.ExpandLayout
import com.yzt.discover.R

/**
 * 发现详情-推荐
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailLeftAdapter(data: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?) :
    BaseQuickAdapter<DiscoverDetailLeftBean.Item.Data.Content, BaseViewHolder>(
        R.layout.item_discover_detail,
        data
    ) {

    override fun convert(holder: BaseViewHolder, item: DiscoverDetailLeftBean.Item.Data.Content) {
        val data = item.data
        data?.let { dataBean ->
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
            val player = holder.getView<StandardGSYVideoPlayer>(R.id.player)
            dataBean.author?.icon?.let {
                ImageUtil.showCircle(context, ivIcon, it)
            }
            dataBean.author?.name?.let {
                tvName.text = it
            }
            dataBean.title?.let {
                val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                val spannableString =
                    SpannableString(context.getString(R.string.discover_release, it))
                val colorSpan = ForegroundColorSpan(context.color(R.color.app_black))
                spannableString.setSpan(
                    colorSpan,
                    context.getString(R.string.discover_release, it).indexOf(" "),
                    context.getString(R.string.discover_release, it).length,
                    flags
                )
                val styleSpan = StyleSpan(Typeface.BOLD)
                spannableString.setSpan(
                    styleSpan,
                    context.getString(R.string.discover_release, it).indexOf(" "),
                    context.getString(R.string.discover_release, it).length,
                    flags
                )
                tvTitle.text = spannableString
            }
            dataBean.description?.let {
                tvDesc.setText(
                    it,
                    dataBean.isExpand,
                    object : ExpandLayout.OnExpandListener {
                        override fun expandChange() {
                            if (!dataBean.isExpand) {
                                dataBean.isExpand = true
                                notifyItemChanged(position)
                            }
                        }
                    })
            }
            dataBean.tags?.let {
                if (flexBox.childCount > 0) {
                    return
                }

                for (i in it.indices) {
                    it[i].name?.let { itt ->
                        val tv = AppCompatTextView(context)
                        tv.text = itt
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
                            context.dimensionPixelOffset(R.dimen.dp_4),
                            context.dimensionPixelOffset(R.dimen.dp_2),
                            context.dimensionPixelOffset(R.dimen.dp_4),
                            context.dimensionPixelOffset(R.dimen.dp_2)
                        )
                        flexBox.addView(tv)
                    }
                }
            }
            if (dataBean.consumption?.collectionCount == null) {
                tvFavorite.text = "0"
            } else {
                tvFavorite.text = dataBean.consumption!!.collectionCount.toString()
            }
            if (dataBean.consumption?.replyCount == null) {
                tvReply.text = "0"
            } else {
                tvReply.text = dataBean.consumption!!.replyCount.toString()
            }
            if (position == itemCount - 1) {
                vLine.visibility = View.GONE
            }
            dataBean.playUrl?.let {
                val ivCover = ImageView(context)
                ivCover.scaleType = ImageView.ScaleType.CENTER_CROP
                dataBean.cover?.feed?.let { itt ->
                    ImageUtil.show(context, ivCover, itt)
                }
                player.thumbImageView = ivCover
                player.setUp(it, false, null, null)
                player.titleTextView.visibility = View.GONE
                player.backButton.visibility = View.GONE
                player.playPosition = position
                player.setIsTouchWiget(false)
                player.setGSYVideoProgressListener { _, _, currentPosition, _ ->
                    run {
                        tvPlayTime.text = CommonUtil.stringForTime(currentPosition)
                    }
                }
            }
        }
    }

}