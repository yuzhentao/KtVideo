package com.yzt.home.adapter

import android.graphics.Typeface
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.bean.HomeBean
import com.yzt.common.util.ImageUtil
import com.yzt.home.R

class HomeAdapter(data: MutableList<HomeBean.Issue.Item>?) :
    BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(R.layout.item_home, data) {

    override fun convert(holder: BaseViewHolder, item: HomeBean.Issue.Item) {
        val img = item.data?.cover?.feed
        img?.let {
            ImageUtil.show(context, holder.getView<AppCompatImageView>(R.id.iv_photo), it)
        }
        val icon = item.data?.author?.icon
        if (icon == null) {
            holder.getView<AppCompatImageView>(R.id.iv_user).visibility = View.GONE
        } else {
            ImageUtil.show(context, holder.getView<AppCompatImageView>(R.id.iv_user), icon)
        }
        val title = item.data?.title
        holder.getView<AppCompatTextView>(R.id.tv_title).text = title
        holder.getView<AppCompatTextView>(R.id.tv_title).typeface =
            Typeface.createFromAsset(context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        val category = item.data?.category
        val duration = item.data?.duration
        duration?.let {
            val minute = it.div(60)
            val second = it.minus((minute.times(60)))
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
            holder.getView<AppCompatTextView>(R.id.tv_content).text = content
        }
    }

}