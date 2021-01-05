package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.ktvideo.bean.SearchBean

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<SearchBean.Item.Data.Content>)

    }

    interface Presenter : BasePresenter {

        fun load(key: String)

    }

}