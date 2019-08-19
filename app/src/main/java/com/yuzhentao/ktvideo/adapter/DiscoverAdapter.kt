package com.yuzhentao.ktvideo.adapter

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.util.ImageUtil

class DiscoverAdapter(data: MutableList<DiscoverBean>?) : BaseQuickAdapter<DiscoverBean, BaseViewHolder>(R.layout.item_discover, data) {

    override fun convert(helper: BaseViewHolder?, item: DiscoverBean?) {
        helper?.let {
            item?.let {
                ImageUtil.show(mContext!!, helper.getView<AppCompatImageView>(R.id.iv_photo), item.bgPicture)
                helper.getView<AppCompatTextView>(R.id.tv).text = item.name
            }
        }
    }

}