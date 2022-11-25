package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.discover.mvp.contract.DiscoverDetailContract
import com.yzt.discover.mvp.model.DiscoverDetailModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 发现详情
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailPresenter(context: Context, view: DiscoverDetailContract.View) :
    DiscoverDetailContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailContract.View? = null
    private val model: DiscoverDetailModel by lazy {
        DiscoverDetailModel()
    }

    private var job: Job? = null

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load(id: String) {
//        context?.let {
//            model.loadData(it, id)
//        }
//            ?.subscribe(object : Observer<DiscoverDetailBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: DiscoverDetailBean) {
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
                val data = model.loadDataByCoroutine(it, id)
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