package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.bean.RankingBean
import com.yzt.ktvideo.mvp.contract.RankingContract
import com.yzt.ktvideo.mvp.model.RankingModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RankingPresenter(context: Context?, view: RankingContract.View) : RankingContract.Presenter {

    private var context: Context? = null
    private var view: RankingContract.View? = null
    private val model: RankingModel by lazy {
        RankingModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        context?.let {
            model
                .loadData(it)
        }
            ?.flatMap { t ->
                val beans: MutableList<RankingBean.TabInfo.Tab> = mutableListOf()
                t.tabInfo.tabList?.let {
                    for (item in it) {
                        item.apiUrl?.let {
                            beans.add(item)
                        }
                    }
                    Observable.just(beans)
                }
            }
            ?.subscribe(object : Observer<MutableList<RankingBean.TabInfo.Tab>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: MutableList<RankingBean.TabInfo.Tab>) {
                    view?.setData(t)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}