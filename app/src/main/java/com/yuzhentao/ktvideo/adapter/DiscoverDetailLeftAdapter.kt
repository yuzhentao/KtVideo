package com.yuzhentao.ktvideo.adapter

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.view.View.TEXT_DIRECTION_LOCALE
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ResourcesUtil
import com.yuzhentao.ktvideo.util.ViewUtil

class DiscoverDetailLeftAdapter(layoutResId: Int, data: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?) : BaseQuickAdapter<DiscoverDetailLeftBean.Item.Data.Content, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: DiscoverDetailLeftBean.Item.Data.Content?) {
        helper?.let {
            val position = helper.layoutPosition
            item?.let {
                val ivIcon = helper.getView<AppCompatImageView>(R.id.iv_icon)
                val tvName = helper.getView<AppCompatTextView>(R.id.tv_name)
                val tvTitle = helper.getView<AppCompatTextView>(R.id.tv_title)
                val tvDesc = helper.getView<AppCompatTextView>(R.id.tv_desc)
                val flexBox = helper.getView<FlexboxLayout>(R.id.flex_box)
                val tvFavorite = helper.getView<AppCompatTextView>(R.id.tv_favorite)
                val tvReply = helper.getView<AppCompatTextView>(R.id.tv_replay)
                val vLine = helper.getView<View>(R.id.v_line)
                val vp = helper.getView<StandardGSYVideoPlayer>(R.id.vp)
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
                item.data?.tags?.let {
                    if (flexBox.childCount > 0) {
                        return
                    }

                    for (i in item.data.tags.indices) {
                        item.data.tags[i].name?.let {
                            val tv = AppCompatTextView(mContext)
                            tv.text = item.data.tags[i].name
                            tv.setTextColor(ContextCompat.getColor(mContext, R.color.pink))
                            tv.setBackgroundResource(R.drawable.shape_tag)
                            tv.ellipsize = TextUtils.TruncateAt.END
                            tv.gravity = Gravity.CENTER
                            tv.includeFontPadding = false
                            tv.maxLines = 1
                            tv.textDirection = TEXT_DIRECTION_LOCALE
                            tv.visibility = View.VISIBLE
                            ViewUtil.setPaddings(tv,
                                    ResourcesUtil.getDimensionPixelOffset(mContext, R.dimen.x4),
                                    ResourcesUtil.getDimensionPixelOffset(mContext, R.dimen.x2),
                                    ResourcesUtil.getDimensionPixelOffset(mContext, R.dimen.x4),
                                    ResourcesUtil.getDimensionPixelOffset(mContext, R.dimen.x2))
                            flexBox.addView(tv)
                        }
                    }
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