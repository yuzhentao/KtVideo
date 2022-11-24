package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.mvp.contract.SplashContract
import com.yzt.ktvideo.mvp.model.SplashModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 闪屏
 *
 * @author yzt 2021/2/9
 */
class SplashPresenter(context: Context, view: SplashContract.View) : SplashContract.Presenter {

    private var context: Context? = null
    private var view: SplashContract.View? = null
    private val model: SplashModel by lazy {
        SplashModel()
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
//            model.loadData(it)
//        }
//            ?.subscribe(object : Observer<SplashBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: SplashBean) {
//                    view?.setData(t)
//                }
//
//                override fun onError(e: Throwable) {
//                    view?.setData(null)
//                }
//            })
        context?.let {
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                Timber.e("loadDataByCoroutine_异常>>>>>$throwable")
                view?.setData(null)
            }
            job = GlobalScope.launch(exceptionHandler) {
                val data = model.loadDataByCoroutine(it)
                if (data == null) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                    view?.setData(null)
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    view?.setData(data)
                }
            }
        }
    }

    override fun cancel() {
        job?.cancel()
    }

}