package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.extension.normalSchedulers
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverDetailRightModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class DiscoverDetailRightPresenter (context: Context, view: DiscoverDetailRightContract.View) : DiscoverDetailRightContract.Presenter {

    var context: Context? = null
    var view: DiscoverDetailRightContract.View? = null
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
        val observable: Observable<DiscoverDetailRightBean>? = context?.let {
            model.loadData(context!!, id)
        }
        observable
                ?.flatMap { t ->
                    val beans: MutableList<DiscoverDetailRightBean.Item.Data.Content> = mutableListOf()
                    for (item in t.itemList) {
                        item.data?.content?.data?.playUrl?.let {
                            beans.add(item.data.content)
                        }
                    }
                    Observable.just(beans)
                }
                ?.normalSchedulers()
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