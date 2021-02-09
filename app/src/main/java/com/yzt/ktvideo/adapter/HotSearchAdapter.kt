package com.yzt.ktvideo.adapter

import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzt.ktvideo.R

/**
 * 热门搜索词
 *
 * @author yzt 2021/2/9
 */
class HotSearchAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        val tv = holder.getView<AppCompatTextView>(R.id.tv)
        tv.text = item
    }

}