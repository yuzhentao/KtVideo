package com.yzt.ktvideo.mvp.contract

import com.yzt.bean.VideoRelatedBean
import com.yzt.common.base.BasePresenter
import com.yzt.common.base.BaseView

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2021/2/9
 */
interface VideoRelatedContract {

    interface View : BaseView<Presenter> {

        fun setData(beans: MutableList<VideoRelatedBean.Item.Data>?)

    }

    interface Presenter : BasePresenter {

        fun load(id: String)

        fun cancel()

    }

}