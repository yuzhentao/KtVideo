package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.VideoRelatedBean

interface VideoRelatedContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}