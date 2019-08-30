package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.VideoRelatedBean

interface VideoRelatedContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}