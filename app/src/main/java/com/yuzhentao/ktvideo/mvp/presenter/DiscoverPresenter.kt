package com.yuzhentao.ktvideo.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable

class DiscoverPresenter(context: Context?, view: DiscoverContract.View) : DiscoverContract.Presenter {

    var context: Context? = null
    var view: DiscoverContract.View? = null
    private val model: DiscoverModel by lazy {
        DiscoverModel()
    }

    init {
        this.view = view
        this.context = context
    }

    override fun start() {

    }

    @SuppressLint("CheckResult")
    override fun load() {
        val observable: Observable<MutableList<DiscoverBean>>? = context?.let {
            model.loadData(context!!)
        }
        observable?.normalSchedulers()?.subscribe { beans: MutableList<DiscoverBean> ->
            view?.setData(beans)
        }
    }

}