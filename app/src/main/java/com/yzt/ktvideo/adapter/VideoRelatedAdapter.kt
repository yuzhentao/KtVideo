package com.yzt.ktvideo.adapter

import android.graphics.Typeface
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.bean.VideoRelatedBean
import com.yzt.common.util.ImageUtil
import com.yzt.ktvideo.R

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2021/2/9
 */
class VideoRelatedAdapter(data: MutableList<VideoRelatedBean.Item.Data>?) :
    BaseQuickAdapter<VideoRelatedBean.Item.Data, BaseViewHolder>(
        R.layout.item_video_related, data
    ) {

    override fun convert(holder: BaseViewHolder, item: VideoRelatedBean.Item.Data) {
        val position = holder.layoutPosition
        holder.getView<AppCompatTextView>(R.id.tv_related).visibility =
            if (position == headerLayoutCount) {
                View.VISIBLE
            } else View.GONE
        holder.getView<AppCompatTextView>(R.id.tv_related).typeface =
            Typeface.createFromAsset(
                context.assets,
                "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
            )
        val img = item.cover?.feed
        img?.let {
            ImageUtil.showRoundedCorners(
                context,
                holder.getView<AppCompatImageView>(R.id.iv),
                it,
                R.dimen.dp_6
            )
        }
        val title = item.title
        holder.getView<AppCompatTextView>(R.id.tv_top).text = title
        holder.getView<AppCompatTextView>(R.id.tv_top).typeface = Typeface.createFromAsset(
            context.assets,
            "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
        )
        val category = "#" + item.category
        holder.getView<AppCompatTextView>(R.id.tv_bottom).text = category
    }

}