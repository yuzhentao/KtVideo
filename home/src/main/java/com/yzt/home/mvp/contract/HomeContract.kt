package com.yzt.home.mvp.contract

import com.yzt.bean.HomeBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: HomeBean)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun loadMore(date: String?)

    }

}