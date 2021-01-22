package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.bean.DiscoverDetailRightBean

interface DiscoverDetailRightContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailRightBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}