package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.ktvideo.bean.DiscoverDetailBean

interface DiscoverDetailContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: DiscoverDetailBean?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}