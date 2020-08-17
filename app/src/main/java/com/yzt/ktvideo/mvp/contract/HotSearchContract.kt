package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView

interface HotSearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<String>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}