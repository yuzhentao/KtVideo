package com.yzt.discover.mvp.contract

import com.yzt.bean.DiscoverBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 发现
 *
 * @author yzt 2021/2/9
 */
interface DiscoverContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<DiscoverBean.Item.Data>)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun cancel()

    }

}