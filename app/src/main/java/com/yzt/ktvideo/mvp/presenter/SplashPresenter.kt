package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.bean.SplashBean
import com.yzt.ktvideo.mvp.contract.SplashContract
import com.yzt.ktvideo.mvp.model.SplashModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SplashPresenter(context: Context, view: SplashContract.View) : SplashContract.Presenter {

    private var context: Context? = null
    private var view: SplashContract.View? = null
    private val model: SplashModel by lazy {
        SplashModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        context?.let {
            model.loadData(it)
        }
            ?.subscribe(object : Observer<SplashBean> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SplashBean) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {
                    view?.setData(null)
                }
            })
    }

}