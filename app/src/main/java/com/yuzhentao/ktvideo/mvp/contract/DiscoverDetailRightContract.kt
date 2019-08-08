package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean

interface DiscoverDetailRightContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: DiscoverDetailRightBean?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}