package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.SearchBean
import com.yuzhentao.ktvideo.mvp.contract.SearchContract
import com.yuzhentao.ktvideo.mvp.model.SearchModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SearchPresenter(context: Context, view: SearchContract.View) : SearchContract.Presenter {

    var context: Context? = null
    var view: SearchContract.View? = null
    private val model: SearchModel by lazy {
        SearchModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(key: String) {
        val observable: Observable<MutableList<SearchBean>>? = context?.let {
            model.loadData(context!!, key)
        }
        observable?.normalSchedulers()?.subscribe(object : Observer<MutableList<SearchBean>> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: MutableList<SearchBean>) {
                view?.setData(t)
            }

            override fun onError(e: Throwable) {

            }
        })
    }

}