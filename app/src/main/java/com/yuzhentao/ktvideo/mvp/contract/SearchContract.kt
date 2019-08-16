package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.SearchBean

interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<SearchBean>)

    }

    interface Presenter : BasePresenter {

        fun load(key: String)

    }

}