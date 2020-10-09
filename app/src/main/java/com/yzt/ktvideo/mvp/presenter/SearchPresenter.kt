package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.bean.SearchBean
import com.yzt.ktvideo.mvp.contract.SearchContract
import com.yzt.ktvideo.mvp.model.SearchModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class SearchPresenter(context: Context, view: SearchContract.View) : SearchContract.Presenter {

    private var context: Context? = null
    private var view: SearchContract.View? = null
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
        context?.let {
            model.loadData(it, key)
        }
            ?.flatMap { t ->
                val beans: MutableList<SearchBean.Item.Data.Content> = mutableListOf()
                for (item in t.itemList) {
                    item.data?.content?.data?.playUrl?.let {
                        beans.add(item.data.content)
                    }
                }
                Observable.just(beans)
            }
            ?.subscribe(object : Observer<MutableList<SearchBean.Item.Data.Content>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: MutableList<SearchBean.Item.Data.Content>) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}