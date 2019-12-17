package com.yuzhentao.ktvideo.adapter

import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.extension.color
import com.yuzhentao.ktvideo.util.ImageUtil

class DiscoverAdapter(data: MutableList<DiscoverBean.Item.Data>?) :
    BaseQuickAdapter<DiscoverBean.Item.Data, BaseViewHolder>(R.layout.item_discover, data) {

    override fun convert(helper: BaseViewHolder, item: DiscoverBean.Item.Data?) {
        item?.let {
            val iv = helper.getView<AppCompatImageView>(R.id.iv)
            item.image?.let {
                ImageUtil.show(mContext!!, iv, item.image)
            }
            iv.setColorFilter(mContext.color(R.color.black_25))
            helper.getView<AppCompatTextView>(R.id.tv).text = item.title
            helper.itemView.setOnTouchListener { v, event ->
                when (event?.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        iv.setColorFilter(mContext.color(R.color.black_10))
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                        iv.setColorFilter(mContext.color(R.color.black_25))
                    }
                    else -> v?.performClick()
                }
                false
            }
        }
    }

}