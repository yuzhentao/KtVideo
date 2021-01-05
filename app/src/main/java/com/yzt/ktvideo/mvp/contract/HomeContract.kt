package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.ktvideo.bean.HomeBean

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: HomeBean)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun loadMore(date: String?)

    }

}