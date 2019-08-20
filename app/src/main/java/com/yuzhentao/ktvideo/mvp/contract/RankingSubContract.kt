package com.yuzhentao.ktvideo.mvp.contract

import com.yuzhentao.ktvideo.base.BasePresenter
import com.yuzhentao.ktvideo.base.BaseView
import com.yuzhentao.ktvideo.bean.RankingSubBean

interface RankingSubContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<RankingSubBean.Item.Data.Content.DataX>?)

    }

    interface Presenter : BasePresenter {

        fun load(strategy: String)

    }

}