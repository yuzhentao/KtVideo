package com.yuzhentao.ktvideo.adapter

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.ResourcesUtil
import com.yuzhentao.ktvideo.util.ViewUtil
import java.text.SimpleDateFormat
import java.util.*

class WatchAdapter(data: MutableList<VideoBean>?) : BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_watch, data) {

    override fun convert(helper: BaseViewHolder?, item: VideoBean?) {
        helper?.let {
            item?.let {
                val position = helper.layoutPosition
                if (position != itemCount - 1) {
                    ViewUtil.setMargins(helper.itemView, 0, 0, 0, ResourcesUtil.getDimensionPixelOffset(mContext!!, R.dimen.x2))
                } else {
                    ViewUtil.setMargins(helper.itemView, 0, 0, 0, 0)
                }
                val photoUrl: String? = item.feed
                photoUrl?.let {
                    ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv), photoUrl)
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

}