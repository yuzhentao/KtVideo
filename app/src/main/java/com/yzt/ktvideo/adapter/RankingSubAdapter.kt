package com.yzt.ktvideo.adapter

import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.ktvideo.R
import com.yzt.ktvideo.bean.RankingSubBean
import com.yzt.common.extension.color
import com.yzt.common.util.ImageUtil

class RankingSubAdapter(data: MutableList<RankingSubBean.Item.Data.Content.DataX>?) :
    BaseQuickAdapter<RankingSubBean.Item.Data.Content.DataX, BaseViewHolder>(
        R.layout.item_ranking_sub,
        data
    ) {

    override fun convert(holder: BaseViewHolder, item: RankingSubBean.Item.Data.Content.DataX) {
        val iv = holder.getView<AppCompatImageView>(R.id.iv)
        val img = item.cover?.feed
        img?.let {
            ImageUtil.show(context, iv, it)
        }
        iv.setColorFilter(context.color(R.color.black_25))
        val title = item.title
        holder.getView<AppCompatTextView>(R.id.tv_title).text = title
        val category = item.category
        val duration = item.duration
        val minute = duration.div(60)
        val second = duration.minus((minute.times(60)).toLong())
        val realMinute: String
        val realSecond: String
        realMinute = if (minute < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        realSecond = if (second < 10) {
            "0$second"
        } else {
            second.toString()
        }
        val content = "$category / $realMinute'$realSecond''"
        holder.getView<AppCompatTextView>(R.id.tv_time).text = content
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