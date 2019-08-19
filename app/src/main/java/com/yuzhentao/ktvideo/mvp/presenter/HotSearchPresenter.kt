package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.mvp.contract.HotSearchContract
import com.yuzhentao.ktvideo.mvp.model.HotSearchModel
import com.yuzhentao.ktvideo.extension.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HotSearchPresenter(context: Context, view: HotSearchContract.View) : HotSearchContract.Presenter {

    var context: Context? = null
    var view: HotSearchContract.View? = null
    private val model: HotSearchModel by lazy {
        HotSearchModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        val observable: Observable<MutableList<String>>? = context?.let {
            model.loadData(context!!)
        }
        observable?.normalSchedulers()?.subscribe(object : Observer<MutableList<String>> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: MutableList<String>) {
                view?.setData(t)
            }

            override fun onError(e: Throwable) {

            }
        })
    }

}