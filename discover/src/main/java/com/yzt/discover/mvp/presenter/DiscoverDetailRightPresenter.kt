package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.discover.mvp.contract.DiscoverDetailRightContract
import com.yzt.discover.mvp.model.DiscoverDetailRightModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverDetailRightPresenter(context: Context, view: DiscoverDetailRightContract.View) :
    DiscoverDetailRightContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailRightContract.View? = null
    private val model: DiscoverDetailRightModel by lazy {
        DiscoverDetailRightModel()
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
                val beans: MutableList<DiscoverDetailRightBean.Item.Data.Content> = mutableListOf()
                for (item in t.itemList) {
                    item.data?.content?.data?.playUrl?.let {
                        beans.add(item.data!!.content!!)
                    }
                }
                Observable.just(beans)
            }
            ?.subscribe(object : Observer<MutableList<DiscoverDetailRightBean.Item.Data.Content>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: MutableList<DiscoverDetailRightBean.Item.Data.Content>) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}