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
        val observable: Observable<SearchBean>? = context?.let {
            model.loadData(context!!, key)
        }
        observable
                ?.flatMap { t ->
                    val beans: ArrayList<SearchBean.Item.Data.Content> = ArrayList()
                    for (item in t.itemList) {
                        item.data?.content?.data?.playUrl?.let {
                            beans.add(item.data.content)
                        }
                    }
                    Observable.just(beans)
                }
                ?.normalSchedulers()?.subscribe(object : Observer<ArrayList<SearchBean.Item.Data.Content>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: ArrayList<SearchBean.Item.Data.Content>) {
                        view?.setData(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

}