package com.yzt.discover.mvp.contract

import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 发现详情-广场
 *
 * @author yzt 2021/2/9
 */
interface DiscoverDetailRightContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverDetailRightBean.Item.Data.Content>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

    }

}