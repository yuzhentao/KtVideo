package com.yzt.ranking.mvp.presenter

import android.content.Context
import com.yzt.bean.RankingBean
import com.yzt.ranking.mvp.contract.RankingContract
import com.yzt.ranking.mvp.model.RankingModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 排行
 *
 * @author yzt 2021/2/9
 */
class RankingPresenter(context: Context?, view: RankingContract.View) : RankingContract.Presenter {

    private var context: Context? = null
    private var view: RankingContract.View? = null
    private val model: RankingModel by lazy {
        RankingModel()
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
//            model
//                .loadData(it)
//        }
//            ?.flatMap { t ->
//                val beans: MutableList<RankingBean.TabInfo.Tab> = mutableListOf()
//                t.tabInfo.tabList?.let {
//                    for (item in it) {
//                        item.apiUrl?.let {
//                            beans.add(item)
//                        }
//                    }
//                    Observable.just(beans)
//                }
//            }
//            ?.subscribe(object : Observer<MutableList<RankingBean.TabInfo.Tab>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<RankingBean.TabInfo.Tab>) {
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
                val beans: MutableList<RankingBean.TabInfo.Tab> = mutableListOf()
                data?.tabInfo?.tabList?.let {
                    for (item in it) {
                        item.apiUrl?.let {
                            beans.add(item)
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