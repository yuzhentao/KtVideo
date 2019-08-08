package com.yuzhentao.ktvideo.mvp.presenter

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailRightContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverDetailRightModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber

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
        observable?.normalSchedulers()?.subscribe(object : Observer<DiscoverDetailRightBean> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: DiscoverDetailRightBean) {
                view?.setData(t)
            }

            override fun onError(e: Throwable) {
                Timber.e(">>>${e.message}")
            }
        })
    }

}