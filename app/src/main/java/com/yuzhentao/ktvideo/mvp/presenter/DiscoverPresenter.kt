package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.extension.normalSchedulers
import com.yuzhentao.ktvideo.mvp.contract.DiscoverContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverPresenter(context: Context?, view: DiscoverContract.View) : DiscoverContract.Presenter {

    var context: Context? = null
    var view: DiscoverContract.View? = null
    private val model: DiscoverModel by lazy {
        DiscoverModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        val observable: Observable<DiscoverBean>? = context?.let {
            model.loadData(context!!)
        }
        observable
                ?.flatMap { t ->
                    val beans: MutableList<DiscoverBean.Item.Data> = mutableListOf()
                    for (item in t.itemList) {
                        item.data?.let {
                            if (it.id > 0) {
                                beans.add(it)
                            }
                        }
                    }
                    Observable.just(beans)
                }
                ?.normalSchedulers()
                ?.subscribe(object : Observer<MutableList<DiscoverBean.Item.Data>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: MutableList<DiscoverBean.Item.Data>) {
                        view?.setData(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

}