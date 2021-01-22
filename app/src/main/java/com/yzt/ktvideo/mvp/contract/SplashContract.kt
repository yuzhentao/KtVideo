package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.bean.SplashBean

interface SplashContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: SplashBean?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}