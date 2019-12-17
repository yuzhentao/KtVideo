package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.SplashBean

interface SplashContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: SplashBean?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}