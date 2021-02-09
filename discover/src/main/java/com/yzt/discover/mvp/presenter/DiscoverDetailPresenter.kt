package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.bean.DiscoverDetailBean
import com.yzt.discover.mvp.contract.DiscoverDetailContract
import com.yzt.discover.mvp.model.DiscoverDetailModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 发现详情
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailPresenter(context: Context, view: DiscoverDetailContract.View) :
    DiscoverDetailContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailContract.View? = null
    private val model: DiscoverDetailModel by lazy {
        DiscoverDetailModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(id: String) {
        context?.let {
            model.loadData(it, id)
        }
            ?.subscribe(object : Observer<DiscoverDetailBean> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: DiscoverDetailBean) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}