package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.bean.DiscoverBean
import com.yzt.discover.mvp.contract.DiscoverContract
import com.yzt.discover.mvp.model.DiscoverModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 发现
 *
 * @author yzt 2021/2/9
 */
class DiscoverPresenter(context: Context?, view: DiscoverContract.View) :
    DiscoverContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverContract.View? = null
    private val model: DiscoverModel by lazy {
        DiscoverModel()
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
//            ?.flatMap { t ->
//                val beans: MutableList<DiscoverBean.Item.Data> = mutableListOf()
//                for (item in t.itemList) {
//                    item.data?.let {
//                        if (it.id > 0) {
//                            beans.add(it)
//                        }
//                    }
//                }
//                Observable.just(beans)
//            }
//            ?.subscribe(object : Observer<MutableList<DiscoverBean.Item.Data>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<DiscoverBean.Item.Data>) {
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
                val data = model.loadDataByCoroutine(it)
                val beans: MutableList<DiscoverBean.Item.Data> = mutableListOf()
                data?.let {
                    for (item in data.itemList) {
                        item.data?.let {
                            if (it.id > 0) {
                                beans.add(it)
                            }
                        }
                    }
                }
                if (beans.isEmpty()) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    withContext(Dispatchers.Main) {
                        view?.setData(beans)
                    }
                }
            }
        }
    }

    override fun cancel() {
        job?.cancel()
    }

}