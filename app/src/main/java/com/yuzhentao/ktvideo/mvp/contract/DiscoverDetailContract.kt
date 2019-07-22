package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean

interface DiscoverDetailContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: DiscoverDetailBean)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}