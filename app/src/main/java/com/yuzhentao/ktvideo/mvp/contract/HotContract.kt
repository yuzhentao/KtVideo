package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.HotBean

interface HotContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: HotBean)

    }

    interface Presenter : BasePresenter {

        fun load(strategy: String)

    }

}