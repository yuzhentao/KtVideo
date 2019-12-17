package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean

interface DiscoverDetailLeftContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}