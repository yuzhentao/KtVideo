package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView
import com.yzt.bean.RankingBean

interface RankingContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}