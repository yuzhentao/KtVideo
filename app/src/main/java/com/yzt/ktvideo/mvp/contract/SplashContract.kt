package com.yzt.ktvideo.mvp.contract

import com.yzt.bean.SplashBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 闪屏
 *
 * @author yzt 2021/2/9
 */
interface SplashContract {

    interface View : BaseView<Presenter> {

        fun setData(bean: SplashBean?)

    }

    interface Presenter : BasePresenter {

        fun load()

        fun cancel()

    }

}