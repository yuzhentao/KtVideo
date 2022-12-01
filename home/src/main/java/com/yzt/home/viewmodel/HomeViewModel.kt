package com.yzt.home.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.HomeBean
import com.yzt.home.repository.HomeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 首页
 *
 * @author yzt 2022/12/1
 */
class HomeViewModel : ViewModel() {

    val liveData: MutableLiveData<HomeBean> by lazy {
        MutableLiveData<HomeBean>()
    }

    fun load(context: Context?) {
//        context?.let {
//            model.loadData(it, true, "")
//        }
//            ?.subscribe(object : Observer<HomeBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: HomeBean) {
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
                val data = HomeRepository.loadDataByCoroutine(it, true, "")
                if (data == null) {
                    Timber.e("loadDataByCoroutine_失败>>>>>")
                } else {
                    Timber.e("loadDataByCoroutine_成功>>>>>")
                    liveData.value = data
                }
            }
        }
    }

    fun loadMore(context: Context?, date: String?) {
//        context?.let {
//            model.loadData(it, false, date)
//        }
//            ?.subscribe(object : Observer<HomeBean> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: HomeBean) {
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
                val data = HomeRepository.loadDataByCoroutine(it, false, date)
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