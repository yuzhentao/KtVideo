package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.SplashBean
import com.yuzhentao.ktvideo.mvp.contract.SplashContract
import com.yuzhentao.ktvideo.mvp.model.SplashModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SplashPresenter(context: Context, view: SplashContract.View) : SplashContract.Presenter {

    var context: Context? = null
    var view: SplashContract.View? = null
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
        val observable: Observable<SplashBean>? = context?.let {
            model.loadData(context!!)
        }
        observable?.normalSchedulers()?.subscribe(object : Observer<SplashBean> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: SplashBean) {
                view?.setData(t)
            }

            override fun onError(e: Throwable) {

            }
        })
    }

}