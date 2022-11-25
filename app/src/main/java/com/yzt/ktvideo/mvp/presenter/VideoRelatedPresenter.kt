package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.bean.VideoRelatedBean
import com.yzt.ktvideo.mvp.contract.VideoRelatedContract
import com.yzt.ktvideo.mvp.model.VideoRelatedModel
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2021/2/9
 */
class VideoRelatedPresenter(context: Context?, view: VideoRelatedContract.View) :
    VideoRelatedContract.Presenter {

    private var context: Context? = null
    private var view: VideoRelatedContract.View? = null
    private val model: VideoRelatedModel by lazy {
        VideoRelatedModel()
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
//            model
//                .loadData(it, id)
//        }
//            ?.flatMap { t ->
//                val beans: MutableList<VideoRelatedBean.Item.Data> = mutableListOf()
//                for (item in t.itemList!!) {
//                    item.data?.playUrl?.let {
//                        beans.add(item.data!!)
//                    }
//                }
//                Observable.just(beans)
//            }
//            ?.subscribe(object : Observer<MutableList<VideoRelatedBean.Item.Data>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<VideoRelatedBean.Item.Data>) {
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
                val beans: MutableList<VideoRelatedBean.Item.Data> = mutableListOf()
                for (item in data?.itemList!!) {
                    item.data?.playUrl?.let {
                        beans.add(item.data!!)
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