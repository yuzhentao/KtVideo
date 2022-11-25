package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.discover.mvp.contract.DiscoverDetailRightContract
import com.yzt.discover.mvp.model.DiscoverDetailRightModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 发现详情-广场
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailRightPresenter(context: Context, view: DiscoverDetailRightContract.View) :
    DiscoverDetailRightContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailRightContract.View? = null
    private val model: DiscoverDetailRightModel by lazy {
        DiscoverDetailRightModel()
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
//            ?.flatMap { t ->
//                val beans: MutableList<DiscoverDetailRightBean.Item.Data.Content> = mutableListOf()
//                for (item in t.itemList) {
//                    item.data?.content?.data?.playUrl?.let {
//                        beans.add(item.data!!.content!!)
//                    }
//                }
//                Observable.just(beans)
//            }
//            ?.subscribe(object : Observer<MutableList<DiscoverDetailRightBean.Item.Data.Content>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<DiscoverDetailRightBean.Item.Data.Content>) {
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
                val beans: MutableList<DiscoverDetailRightBean.Item.Data.Content> = mutableListOf()
                data?.let {
                    for (item in data.itemList) {
                        item.data?.content?.data?.playUrl?.let {
                            beans.add(item.data!!.content!!)
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