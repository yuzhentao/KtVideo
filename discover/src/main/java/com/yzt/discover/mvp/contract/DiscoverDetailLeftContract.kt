package com.yzt.discover.mvp.contract

import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 发现详情-推荐
 *
 * @author yzt 2021/2/9
 */
interface DiscoverDetailLeftContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

        fun cancel()

    }

}