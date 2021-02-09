package com.yzt.ranking.mvp.contract

import com.yzt.bean.RankingBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 排行
 *
 * @author yzt 2021/2/9
 */
interface RankingContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<RankingBean.TabInfo.Tab>?)

    }

    interface Presenter : BasePresenter {

        fun load()

    }

}