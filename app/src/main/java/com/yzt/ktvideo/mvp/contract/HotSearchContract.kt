package com.yzt.ktvideo.mvp.contract

import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 热门搜索词
 *
 * @author yzt 2021/2/9
 */
interface HotSearchContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<String>?)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun cancel()

    }

}