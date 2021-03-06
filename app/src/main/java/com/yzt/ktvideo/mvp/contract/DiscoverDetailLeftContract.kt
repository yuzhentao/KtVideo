package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.DiscoverDetailLeftBean

interface DiscoverDetailLeftContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}