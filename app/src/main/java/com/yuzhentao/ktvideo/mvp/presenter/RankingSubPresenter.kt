package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.RankingSubBean
import com.yuzhentao.ktvideo.mvp.contract.RankingSubContract
import com.yuzhentao.ktvideo.mvp.model.RankingSubModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RankingSubPresenter(context: Context?, view: RankingSubContract.View) : RankingSubContract.Presenter {

    private var context: Context? = null
    private var view: RankingSubContract.View? = null
    private val model: RankingSubModel by lazy {
        RankingSubModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(strategy: String) {
        context?.let {
            model.loadData(context!!, strategy)
        }
                ?.flatMap { t ->
                    val beans: MutableList<RankingSubBean.Item.Data.Content.DataX> = mutableListOf()
                    for (item in t.itemList) {
                        item.data?.content?.data?.let {
                            beans.add(it)
                        }
                    }
                    Observable.just(beans)
                }
                ?.subscribe(object : Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: MutableList<RankingSubBean.Item.Data.Content.DataX>) {
                        view?.setData(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

}