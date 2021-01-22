package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.bean.VideoRelatedBean

interface VideoRelatedContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}