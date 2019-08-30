package com.yuzhentao.ktvideo.adapter

import android.graphics.Typeface
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoRelatedBean
import com.yuzhentao.ktvideo.util.ImageUtil

class VideoRelatedAdapter(data: MutableList<VideoRelatedBean.Item.Data>?) :
    BaseQuickAdapter<VideoRelatedBean.Item.Data, BaseViewHolder>(
        R.layout.item_video_related, data
    ) {

    override fun convert(helper: BaseViewHolder?, item: VideoRelatedBean.Item.Data?) {
        helper?.let {
            item?.let {
                val position = helper.layoutPosition
                helper.getView<AppCompatTextView>(R.id.tv_related).visibility =
                    if (position == headerLayoutCount) {
                        View.VISIBLE
                    } else View.GONE
                helper.getView<AppCompatTextView>(R.id.tv_related).typeface =
                    Typeface.createFromAsset(
                        mContext.assets,
                        "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
                    )
                val img = item.cover?.feed
                img?.let {
                    ImageUtil.showRoundedCorners(
                        mContext!!,
                        helper.getView<AppCompatImageView>(R.id.iv),
                        img,
                        R.dimen.x6
                    )
                }
                val title = item.title
                helper.getView<AppCompatTextView>(R.id.tv_top).text = title
                helper.getView<AppCompatTextView>(R.id.tv_top).typeface = Typeface.createFromAsset(
                    mContext.assets,
                    "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"
                )
                val category = "#" + item.category
                helper.getView<AppCompatTextView>(R.id.tv_bottom).text = category
            }
        }
    }

}