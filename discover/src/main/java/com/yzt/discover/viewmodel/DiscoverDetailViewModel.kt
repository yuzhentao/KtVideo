package com.yzt.discover.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.DiscoverDetailBean
import com.yzt.discover.repository.DiscoverDetailRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 发现-详情
 *
 * @author yzt 2022/12/1
 */
class DiscoverDetailViewModel : ViewModel() {

    val liveData: MutableLiveData<DiscoverDetailBean> by lazy {
        MutableLiveData<DiscoverDetailBean>()
    }

    fun load(context: Context?, id: String) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = DiscoverDetailRepository.loadDataByCoroutine(it, id)
                if (data == null) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    withContext(Dispatchers.Main) {
                        liveData.value = data
                    }
                }
            }
        }
    }

}