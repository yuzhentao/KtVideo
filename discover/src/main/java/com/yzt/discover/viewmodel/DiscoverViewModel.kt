package com.yzt.discover.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.DiscoverBean
import com.yzt.discover.repository.DiscoverRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 发现
 *
 * @author yzt 2022/12/1
 */
class DiscoverViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<DiscoverBean.Item.Data>> by lazy {
        MutableLiveData<MutableList<DiscoverBean.Item.Data>>()
    }

    fun load(context: Context?) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = DiscoverRepository.loadDataByCoroutine(it)
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
                        liveData.value = beans
                    }
                }
            }
        }
    }

}