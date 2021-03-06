package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.bean.DiscoverDetailLeftBean
import com.yzt.ktvideo.mvp.contract.DiscoverDetailLeftContract
import com.yzt.ktvideo.mvp.model.DiscoverDetailLeftModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverDetailLeftPresenter(context: Context, view: DiscoverDetailLeftContract.View) :
    DiscoverDetailLeftContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailLeftContract.View? = null
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
        context?.let {
            model.loadData(it, id)
        }
            ?.flatMap { t ->
                val beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content> = mutableListOf()
                for (item in t.itemList) {
                    item.data?.content?.data?.playUrl?.let {
                        beans.add(item.data.content)
                    }
                }
                Observable.just(beans)
            }
            ?.subscribe(object : Observer<MutableList<DiscoverDetailLeftBean.Item.Data.Content>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: MutableList<DiscoverDetailLeftBean.Item.Data.Content>) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}