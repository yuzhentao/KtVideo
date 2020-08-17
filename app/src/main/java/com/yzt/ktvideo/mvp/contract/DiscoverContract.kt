package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.DiscoverBean

interface DiscoverContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverBean.Item.Data>)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}