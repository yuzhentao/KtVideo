package com.yuzhentao.ktvideo.adapter

import android.graphics.Typeface
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.util.ImageUtil

class HomeAdapter(data: MutableList<HomeBean.Issue.Item>?) : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(R.layout.item_home, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {
        helper?.let {
            item?.let {
                val photo = item.data?.cover?.feed
                val icon = item.data?.author?.icon
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv_photo), photo as String)
                if (icon == null) {
                    helper.getView<AppCompatImageView>(R.id.iv_user).visibility = View.GONE
                } else {
                    ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv_user), icon)
                }
                val title = item.data.title
                val category = item.data.category
                val duration = item.data.duration
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
                helper.getView<AppCompatTextView>(R.id.tv_title).text = title
                helper.getView<AppCompatTextView>(R.id.tv_title).typeface = Typeface.createFromAsset(mContext.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
                helper.getView<AppCompatTextView>(R.id.tv_content).text = content
            }
        }
    }

}