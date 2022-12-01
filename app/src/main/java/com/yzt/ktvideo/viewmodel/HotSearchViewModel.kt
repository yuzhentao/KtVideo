package com.yzt.ktvideo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.ktvideo.repository.HotSearchRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 热门搜索词
 *
 * @author yzt 2022/12/1
 */
class HotSearchViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<String>> by lazy {
        MutableLiveData<MutableList<String>>()
    }

    fun load(context: Context?) {
//        context?.let {
//            model.loadData(it)
//        }
//            ?.subscribe(object : Observer<MutableList<String>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<String>) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = HotSearchRepository.loadDataByCoroutine(it)
                if (data == null || data.isEmpty()) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    liveData.value = data
                }
            }
        }
    }

}