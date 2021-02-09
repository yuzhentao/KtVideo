package com.yzt.ktvideo.mvp.contract

import com.yzt.bean.SearchBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 搜索
 *
 * @author yzt 2021/2/9
 */
interface SearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<SearchBean.Item.Data.Content>)

    }

    interface Presenter : BasePresenter {

        fun load(key: String)

    }

}