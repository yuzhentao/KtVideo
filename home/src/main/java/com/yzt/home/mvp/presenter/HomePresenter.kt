package com.yzt.home.mvp.presenter

import android.content.Context
import com.yzt.home.mvp.contract.HomeContract
import com.yzt.home.mvp.model.HomeModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 首页
 *
 * @author yzt 2021/2/9
 */
class HomePresenter(context: Context?, view: HomeContract.View) : HomeContract.Presenter {

    private var context: Context? = null
    private var view: HomeContract.View? = null
    private val model: HomeModel by lazy {
        HomeModel()
    }

    private var job: Job? = null

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
//        context?.let {
//            model.loadData(it, true, "")
//        }
//            ?.subscribe(object : Observer<HomeBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: HomeBean) {
//                    view?.setData(t)
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//            })
        context?.let {
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                Timber.e("loadDataByCoroutine_异常>>>>>$throwable")
            }
            job = GlobalScope.launch(exceptionHandler) {
                val data = model.loadDataByCoroutine(it, true, "")
                if (data == null) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    view?.setData(data)
                }
            }
        }
    }

    override fun loadMore(date: String?) {
//        context?.let {
//            model.loadData(it, false, date)
//        }
//            ?.subscribe(object : Observer<HomeBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: HomeBean) {
//                    view?.setData(t)
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//            })
        context?.let {
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                Timber.e("loadDataByCoroutine_异常>>>>>$throwable")
            }
            job = GlobalScope.launch(exceptionHandler) {
                val data = model.loadDataByCoroutine(it, false, date)
                if (data == null) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    withContext(Dispatchers.Main) {
                        view?.setData(data)
                    }
                }
            }
        }
    }

    override fun cancel() {
        job?.cancel()
    }

}