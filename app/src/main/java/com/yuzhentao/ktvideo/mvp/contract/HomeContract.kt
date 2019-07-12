package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.NewHomeBean

interface HomeContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: NewHomeBean)

    }

    interface Presenter : BasePresenter {

        fun requestData()

        fun requestMoreData(data: String?)

    }

}