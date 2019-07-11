package com.yuzhentao.ktvideo.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.mvp.contract.HomeContract
import com.yuzhentao.ktvideo.mvp.model.HomeModel
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable

class HomePresenter(context: Context?, view: HomeContract.View) : HomeContract.Presenter {

    var context: Context? = null
    var view: HomeContract.View? = null
    val mModel: HomeModel by lazy {
        HomeModel()
    }

    init {
        this.context = context
        this.view = view
    }

    override fun start() {
        requestData()
    }

    @SuppressLint("CheckResult")
    override fun requestData() {
        val observable: Observable<HomeBean>? = context?.let {
            mModel.loadData(it, true, "0")
        }
        observable?.normalSchedulers()?.subscribe { homeBean: HomeBean ->
            view?.setData(homeBean)
        }
    }

    @SuppressLint("CheckResult")
    override fun requestMoreData(data: String?) {
        val observable: Observable<HomeBean>? = context?.let { mModel.loadData(it, false, data) }
        observable?.normalSchedulers()?.subscribe { homeBean: HomeBean ->
            view?.setData(homeBean)
        }
    }

}