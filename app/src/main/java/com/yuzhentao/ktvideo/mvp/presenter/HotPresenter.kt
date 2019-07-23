package com.yuzhentao.ktvideo.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.mvp.contract.HotContract
import com.yuzhentao.ktvideo.mvp.model.HotModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable

class HotPresenter(context: Context?, view: HotContract.View) : HotContract.Presenter {

    var context: Context? = null
    var view: HotContract.View? = null
    private val model: HotModel by lazy {
        HotModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    @SuppressLint("CheckResult")
    override fun load(strategy: String) {
        val observable: Observable<HotBean>? = context?.let {
            model.loadData(context!!, strategy)
        }
        observable?.normalSchedulers()?.subscribe { bean: HotBean ->
            view?.setData(bean)
        }
    }

}