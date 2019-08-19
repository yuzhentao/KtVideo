package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverDetailModel
import com.yuzhentao.ktvideo.extension.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverDetailPresenter(context: Context, view: DiscoverDetailContract.View) : DiscoverDetailContract.Presenter {

    var context: Context? = null
    var view: DiscoverDetailContract.View? = null
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
        val observable: Observable<DiscoverDetailBean>? = context?.let {
            model.loadData(context!!, id)
        }
        observable?.normalSchedulers()?.subscribe(object : Observer<DiscoverDetailBean> {
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