package com.yuzhentao.ktvideo.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.mvp.contract.DiscoverDetailContract
import com.yuzhentao.ktvideo.mvp.model.DiscoverDetailModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable

class DiscoverDetailPresenter(context: Context, view: DiscoverDetailContract.View) : DiscoverDetailContract.Presenter {

    var context: Context? = null
    var view: DiscoverDetailContract.View? = null
    private val model: DiscoverDetailModel by lazy {
        DiscoverDetailModel()
    }

    init {
        this.view = view
        this.context = context
    }

    override fun start() {

    }

    @SuppressLint("CheckResult")
    override fun load(id: String) {
        val observable: Observable<DiscoverDetailBean>? = context?.let {
            model.loadData(context!!, id)
        }
        observable?.normalSchedulers()?.subscribe { bean: DiscoverDetailBean ->
            view?.setData(bean)
        }
    }

}