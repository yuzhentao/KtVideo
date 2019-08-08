package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean

interface DiscoverDetailLeftContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: DiscoverDetailLeftBean?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}