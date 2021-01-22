package com.yzt.ktvideo.adapter

import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.ktvideo.R
import com.yzt.ktvideo.bean.DiscoverBean
import com.yzt.common.extension.color
import com.yzt.common.util.ImageUtil

class DiscoverAdapter(data: MutableList<DiscoverBean.Item.Data>?) :
    BaseQuickAdapter<DiscoverBean.Item.Data, BaseViewHolder>(R.layout.item_discover, data) {

    override fun convert(holder: BaseViewHolder, item: DiscoverBean.Item.Data) {
        val iv = holder.getView<AppCompatImageView>(R.id.iv)
        item.image?.let {
            ImageUtil.show(context, iv, it)
        }
        iv.setColorFilter(context.color(R.color.black_25))
        holder.getView<AppCompatTextView>(R.id.tv).text = item.title
        holder.itemView.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    iv.setColorFilter(context.color(R.color.black_10))
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                    iv.setColorFilter(context.color(R.color.black_25))
                }
                else -> v?.performClick()
            }
            false
        }
    }

}