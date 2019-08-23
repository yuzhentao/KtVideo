package com.yuzhentao.ktvideo.adapter

import androidx.appcompat.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R

class HotSearchAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            val tv = helper.getView<AppCompatTextView>(R.id.tv)
            tv.text = item
        }
    }

}