package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

interface HotSearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<String>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}