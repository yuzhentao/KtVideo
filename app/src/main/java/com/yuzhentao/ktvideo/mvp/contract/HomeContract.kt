package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.HomeBean

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: HomeBean)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun loadMore(date: String?)

    }

}