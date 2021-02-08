package com.yzt.discover.mvp.contract

import com.yzt.bean.DiscoverDetailBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

interface DiscoverDetailContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: DiscoverDetailBean?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}