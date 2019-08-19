package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.mvp.contract.HotContract
import com.yuzhentao.ktvideo.mvp.model.HotModel
import com.yuzhentao.ktvideo.extension.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HotPresenter(context: Context?, view: HotContract.View) : HotContract.Presenter {

    var context: Context? = null
    var view: HotContract.View? = null
    private val model: HotModel by lazy {
        HotModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(strategy: String) {
        val observable: Observable<HotBean>? = context?.let {
            model.loadData(context!!, strategy)
        }
        observable?.normalSchedulers()?.subscribe(object : Observer<HotBean> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: HotBean) {
                view?.setData(t)
            }

            override fun onError(e: Throwable) {

            }
        })
    }

}