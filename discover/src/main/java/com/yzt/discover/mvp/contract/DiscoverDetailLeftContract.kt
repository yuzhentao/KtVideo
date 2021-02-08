package com.yzt.discover.mvp.contract

import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

interface DiscoverDetailLeftContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}