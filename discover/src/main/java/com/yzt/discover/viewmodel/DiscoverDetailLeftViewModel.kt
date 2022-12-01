package com.yzt.discover.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.discover.repository.DiscoverDetailLeftRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 发现-详情-推荐
 *
 * @author yzt 2022/12/1
 */
class DiscoverDetailLeftViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<DiscoverDetailLeftBean.Item.Data.Content>> by lazy {
        MutableLiveData<MutableList<DiscoverDetailLeftBean.Item.Data.Content>>()
    }

    fun load(context: Context?, id: String) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = DiscoverDetailLeftRepository.loadDataByCoroutine(it, id)
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
                        liveData.value = beans
                    }
                }
            }
        }
    }

}