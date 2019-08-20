package com.yuzhentao.ktvideo.adapter

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.util.ImageUtil

class RankingAdapter(data: MutableList<HotBean.Item.Data>?) : BaseQuickAdapter<HotBean.Item.Data, BaseViewHolder>(R.layout.item_ranking, data) {

    override fun convert(helper: BaseViewHolder?, item: HotBean.Item.Data?) {
        helper?.let {
            item?.let {
                val img = item.cover?.feed
                img?.let {
                    ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv), img)
                }
                val title = item.title
                helper.getView<AppCompatTextView>(R.id.tv_title).text = title
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
                helper.getView<AppCompatTextView>(R.id.tv_time).text = content
            }
        }
    }

}