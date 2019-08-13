package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailLeftBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailLeftContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverDetailLeftModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverDetailLeftPresenter(context: Context, view: DiscoverDetailLeftContract.View) : DiscoverDetailLeftContract.Presenter {

    var context: Context? = null
    var view: DiscoverDetailLeftContract.View? = null
    private val model: DiscoverDetailLeftModel by lazy {
        DiscoverDetailLeftModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(id: String) {
        val observable: Observable<DiscoverDetailLeftBean>? = context?.let {
            model.loadData(context!!, id)
        }
        observable
                ?.flatMap { t ->
                    val beans: ArrayList<DiscoverDetailLeftBean.Item.Data.Content> = ArrayList()
                    for (item in t.itemList) {
                        item.data?.content?.let {
                            beans.add(it)
                        }
                    }
                    Observable.just(beans)
                }
                ?.normalSchedulers()
                ?.subscribe(object : Observer<List<DiscoverDetailLeftBean.Item.Data.Content>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: List<DiscoverDetailLeftBean.Item.Data.Content>) {
                        view?.setData(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

}