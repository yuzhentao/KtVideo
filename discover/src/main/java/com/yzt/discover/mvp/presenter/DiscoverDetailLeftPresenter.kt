package com.yzt.discover.mvp.presenter

import android.content.Context
import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.discover.mvp.contract.DiscoverDetailLeftContract
import com.yzt.discover.mvp.model.DiscoverDetailLeftModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 发现详情-推荐
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailLeftPresenter(context: Context, view: DiscoverDetailLeftContract.View) :
    DiscoverDetailLeftContract.Presenter {

    private var context: Context? = null
    private var view: DiscoverDetailLeftContract.View? = null
    private val model: DiscoverDetailLeftModel by lazy {
        DiscoverDetailLeftModel()
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
//                val beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content> = mutableListOf()
//                for (item in t.itemList) {
//                    item.data?.content?.data?.playUrl?.let {
//                        beans.add(item.data!!.content!!)
//                    }
//                }
//                Observable.just(beans)
//            }
//            ?.subscribe(object : Observer<MutableList<DiscoverDetailLeftBean.Item.Data.Content>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<DiscoverDetailLeftBean.Item.Data.Content>) {
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
                val beans: MutableList<DiscoverDetailLeftBean.Item.Data.Content> = mutableListOf()
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