package com.yzt.ktvideo.mvp.presenter

import android.content.Context
import com.yzt.ktvideo.mvp.contract.HotSearchContract
import com.yzt.ktvideo.mvp.model.HotSearchModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 热门搜索词
 *
 * @author yzt 2021/2/9
 */
class HotSearchPresenter(context: Context, view: HotSearchContract.View) :
    HotSearchContract.Presenter {

    private var context: Context? = null
    private var view: HotSearchContract.View? = null
    private val model: HotSearchModel by lazy {
        HotSearchModel()
    }

    private var job: Job? = null

    init {
        this.context = context
        this.view = view
    }

    override fun start() {

    }

    override fun load() {
        context?.let {
            val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                Timber.e("loadDataByCoroutine_异常>>>>>$throwable")
            }
            job = GlobalScope.launch(exceptionHandler) {
                val data = model.loadDataByCoroutine(it)
                if (data == null || data.isEmpty()) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
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