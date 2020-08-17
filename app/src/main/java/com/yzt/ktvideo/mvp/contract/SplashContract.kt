package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.SplashBean

interface SplashContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: SplashBean?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}