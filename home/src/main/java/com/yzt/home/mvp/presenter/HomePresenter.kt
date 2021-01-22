package com.yzt.home.mvp.presenter

import android.content.Context
import com.yzt.bean.HomeBean
import com.yzt.home.mvp.contract.HomeContract
import com.yzt.home.mvp.model.HomeModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HomePresenter(context: Context?, view: HomeContract.View) : HomeContract.Presenter {

    private var context: Context? = null
    private var view: HomeContract.View? = null
    private val model: HomeModel by lazy {
        HomeModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        context?.let {
            model.loadData(it, true, "")
        }
            ?.subscribe(object : Observer<HomeBean> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: HomeBean) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    override fun loadMore(date: String?) {
        context?.let {
            model.loadData(it, false, date)
        }
            ?.subscribe(object : Observer<HomeBean> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: HomeBean) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}