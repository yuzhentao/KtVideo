package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.mvp.contract.HotSearchContract
import com.yzt.ktvideo.mvp.model.HotSearchModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HotSearchPresenter(context: Context, view: HotSearchContract.View) : HotSearchContract.Presenter {

    private var context: Context? = null
    private var view: HotSearchContract.View? = null
    private val model: HotSearchModel by lazy {
        HotSearchModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        context?.let {
            model.loadData(context!!)
        }
                ?.subscribe(object : Observer<MutableList<String>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: MutableList<String>) {
                        view?.setData(t)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

}