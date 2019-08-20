package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.RankingBean
import com.yuzhentao.ktvideo.extension.normalSchedulers
import com.yuzhentao.ktvideo.mvp.contract.RankingContract
import com.yuzhentao.ktvideo.mvp.model.RankingModel
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RankingPresenter(context: Context?, view: RankingContract.View) : RankingContract.Presenter {

    var context: Context? = null
    var view: RankingContract.View? = null
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
        val observable: Observable<RankingBean>? = context?.let {
            model.loadData(context!!)
        }
        observable
                ?.flatMap { t ->
                    val beans: MutableList<RankingBean.TabInfo.Tab> = mutableListOf()
                    t.tabInfo.tabList?.let {
                        for (item in t.tabInfo.tabList) {
                            item.apiUrl?.let {
                                beans.add(item)
                            }
                        }
                        Observable.just(beans)
                    }
                }
                ?.normalSchedulers()
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