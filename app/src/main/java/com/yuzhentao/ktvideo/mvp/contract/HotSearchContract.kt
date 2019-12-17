package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView

interface HotSearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<String>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}