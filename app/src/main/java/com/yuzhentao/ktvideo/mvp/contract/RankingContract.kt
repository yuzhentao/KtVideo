package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.RankingBean

interface RankingContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}