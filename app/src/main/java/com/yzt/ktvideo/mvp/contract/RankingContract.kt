package com.yzt.ktvideo.mvp.contract

import com.yzt.ktvideo.base.BasePresenter
import com.yzt.ktvideo.base.BaseView
import com.yzt.ktvideo.bean.RankingBean

interface RankingContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}