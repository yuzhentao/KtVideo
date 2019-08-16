package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.SearchBean

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: ArrayList<SearchBean.Item.Data.Content>)

    }

    interface Presenter : BasePresenter {

        fun load(key: String)

    }

}