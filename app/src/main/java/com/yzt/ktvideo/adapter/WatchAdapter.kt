package com.yzt.ktvideo.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.bean.VideoBean
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.util.ImageUtil
import com.yzt.common.util.ViewUtil
import com.yzt.ktvideo.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * 观看记录
 *
 * @author yzt 2021/2/9
 */
class WatchAdapter(data: MutableList<VideoBean>?) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_watch, data) {

    override fun convert(holder: BaseViewHolder, item: VideoBean) {
        val position = holder.layoutPosition
        if (position != itemCount - 1) {
            ViewUtil.setMargins(
                holder.itemView,
                0,
                0,
                0,
                context.dimensionPixelOffset(R.dimen.dp_2)
            )
        } else {
            ViewUtil.setMargins(holder.itemView, 0, 0, 0, 0)
        }
        val img: String? = item.feed
        img?.let {
            ImageUtil.show(context, holder.getView<AppCompatImageView>(R.id.iv), it)
        }
        val title: String? = item.title
        holder.getView<AppCompatTextView>(R.id.tv_top).text = title
        val category: String? = item.category
        val duration = item.duration
        val minute = duration?.div(60)
        val second = minute?.times(60)?.let {
            duration.minus(it)
        }
        val realMinute: String = if (minute!! < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        val realSecond: String = if (second!! < 10) {
            "0$second"
        } else {
            second.toString()
        }
        val releaseTime = item.time
        val smf = SimpleDateFormat("MM-dd", Locale.getDefault())
        val date = smf.format(releaseTime)
        val content = "$category / $realMinute'$realSecond'' / $date"
        holder.getView<AppCompatTextView>(R.id.tv_bottom).text = content
    }

}