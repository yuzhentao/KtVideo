package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.DiscoverBean

interface DiscoverContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverBean>)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}