package com.yuzhentao.ktvideo.adapter

import android.support.v7.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.yuzhentao.ktvideo.R

class SearchAdapter(data: MutableList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.let {
            val tv = helper.getView<AppCompatTextView>(R.id.tv)
            tv.text = item
            val params = tv.layoutParams
            if (params is FlexboxLayoutManager.LayoutParams) {
                params.flexGrow = 1.0F//子元素的放大比例
            }
        }
    }

}