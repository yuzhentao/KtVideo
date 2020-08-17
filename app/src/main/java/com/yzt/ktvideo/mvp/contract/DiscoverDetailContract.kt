package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.DiscoverDetailBean

interface DiscoverDetailContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: DiscoverDetailBean?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}