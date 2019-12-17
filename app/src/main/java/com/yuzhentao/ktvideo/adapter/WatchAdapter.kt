package com.yuzhentao.ktvideo.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.extension.dimensionPixelOffset
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ViewUtil
import java.text.SimpleDateFormat
import java.util.*

class WatchAdapter(data: MutableList<VideoBean>?) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_watch, data) {

    override fun convert(helper: BaseViewHolder, item: VideoBean?) {
        item?.let {
            val position = helper.layoutPosition
            if (position != itemCount - 1) {
                ViewUtil.setMargins(
                    helper.itemView,
                    0,
                    0,
                    0,
                    mContext.dimensionPixelOffset(R.dimen.x2)
                )
            } else {
                ViewUtil.setMargins(helper.itemView, 0, 0, 0, 0)
            }
            val img: String? = item.feed
            img?.let {
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv), img)
            }
            val title: String? = item.title
            helper.getView<AppCompatTextView>(R.id.tv_top)!!.text = title
            val category: String? = item.category
            val duration = item.duration
            val minute = duration?.div(60)
            val second = minute?.times(60)?.let {
                duration.minus(it)
            }
            val realMinute: String
            val realSecond: String
            realMinute = if (minute!! < 10) {
                "0$minute"
            } else {
                minute.toString()
            }
            realSecond = if (second!! < 10) {
                "0$second"
            } else {
                second.toString()
            }
            val releaseTime = item.time
            val smf = SimpleDateFormat("MM-dd", Locale.getDefault())
            val date = smf.format(releaseTime)
            val content = "$category / $realMinute'$realSecond'' / $date"
            helper.getView<AppCompatTextView>(R.id.tv_bottom)!!.text = content
        }
    }

}