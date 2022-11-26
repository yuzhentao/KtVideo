package com.yzt.ranking.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.RankingSubBean
import com.yzt.ranking.repository.RankingSubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 排行-子Fragment
 *
 * @author yzt 2021/2/9
 */
class RankingSubViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<RankingSubBean.Item.Data.Content.DataX>> by lazy {
        MutableLiveData<MutableList<RankingSubBean.Item.Data.Content.DataX>>()
    }

    fun load(context: Context?, strategy: String) {
//        context?.let {
//            RankingSubRepository.loadData(context, strategy)
//        }
//            ?.flatMap { t ->
//                val beans: MutableList<RankingSubBean.Item.Data.Content.DataX> = mutableListOf()
//                for (item in t.itemList) {
//                    item.data?.content?.data?.let {
//                        beans.add(it)
//                    }
//                }
//                Observable.just(beans)
//            }
//            ?.subscribe(object : Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> {
//                override fun onComplete() {
//
//                }
//
//                override fun onSubscribe(d: Disposable) {
//
//                }
//
//                override fun onNext(t: MutableList<RankingSubBean.Item.Data.Content.DataX>) {
//                    liveData.value = t
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
                val data = RankingSubRepository.loadDataByCoroutine(context, strategy)
                val beans: MutableList<RankingSubBean.Item.Data.Content.DataX> = mutableListOf()
                data?.let {
                    for (item in it.itemList) {
                        item.data?.content?.data?.let { bean ->
                            beans.add(bean)
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