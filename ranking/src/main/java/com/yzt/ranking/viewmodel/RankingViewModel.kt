package com.yzt.ranking.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.RankingBean
import com.yzt.ranking.repository.RankingRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 排行
 *
 * @author yzt 2022/11/30
 */
class RankingViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<RankingBean.TabInfo.Tab>> by lazy {
        MutableLiveData<MutableList<RankingBean.TabInfo.Tab>>()
    }

    fun load(context: Context?) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = RankingRepository.loadDataByCoroutine(it)
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
                        liveData.value = beans
                    }
                }
            }
        }
    }

}