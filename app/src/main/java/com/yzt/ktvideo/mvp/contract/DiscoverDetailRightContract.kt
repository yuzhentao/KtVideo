package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.DiscoverDetailRightBean

interface DiscoverDetailRightContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailRightBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}