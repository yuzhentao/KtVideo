package com.yzt.ktvideo.adapter

import android.graphics.Typeface
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yzt.ktvideo.R
import com.yzt.ktvideo.bean.HomeBean
import com.yzt.ktvideo.util.ImageUtil

class HomeAdapter(data: MutableList<HomeBean.Issue.Item>?) :
    BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(R.layout.item_home, data) {

    override fun convert(helper: BaseViewHolder, item: HomeBean.Issue.Item?) {
        item?.let {
            val img = item.data?.cover?.feed
            img?.let {
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv_photo), img)
            }
            val icon = item.data?.author?.icon
            if (icon == null) {
                helper.getView<AppCompatImageView>(R.id.iv_user).visibility = View.GONE
            } else {
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv_user), icon)
            }
            val title = item.data?.title
            helper.getView<AppCompatTextView>(R.id.tv_title).text = title
            helper.getView<AppCompatTextView>(R.id.tv_title).typeface =
                Typeface.createFromAsset(mContext.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
            val category = item.data?.category
            val duration = item.data?.duration
            duration?.let {
                val minute = duration.div(60)
                val second = duration.minus((minute.times(60)))
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
                val content = "发布于 $category / $realMinute:$realSecond"
                helper.getView<AppCompatTextView>(R.id.tv_content).text = content
            }
        }
    }

}