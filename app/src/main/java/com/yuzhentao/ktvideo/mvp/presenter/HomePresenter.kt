package com.yuzhentao.ktvideo.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yuzhentao.ktvideo.bean.NewHomeBean
import com.yuzhentao.ktvideo.mvp.contract.HomeContract
import com.yuzhentao.ktvideo.mvp.model.HomeModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable

class HomePresenter(context: Context?, view: HomeContract.View) : HomeContract.Presenter {

    var context: Context? = null
    var view: HomeContract.View? = null
    private val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    @SuppressLint("CheckResult")
    override fun requestData() {
        val observable: Observable<NewHomeBean>? = context?.let {
            mModel.loadData(it, true, "0")
        }
        observable?.normalSchedulers()?.subscribe { homeBean: NewHomeBean ->
            view?.setData(homeBean)
        }
    }

    @SuppressLint("CheckResult")
    override fun requestMoreData(data: String?) {
        val observable: Observable<NewHomeBean>? = context?.let { mModel.loadData(it, false, data) }
        observable?.normalSchedulers()?.subscribe { homeBean: NewHomeBean ->
            view?.setData(homeBean)
        }
    }

}