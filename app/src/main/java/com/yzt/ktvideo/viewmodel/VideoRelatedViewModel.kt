package com.yzt.ktvideo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yzt.bean.VideoRelatedBean
import com.yzt.ktvideo.repository.VideoRelatedRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2022/12/1
 */
class VideoRelatedViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<VideoRelatedBean.Item.Data>> by lazy {
        MutableLiveData<MutableList<VideoRelatedBean.Item.Data>>()
    }

    fun load(context: Context?, id: String) {
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
            viewModelScope.launch(exceptionHandler) {
                val data = VideoRelatedRepository.loadDataByCoroutine(it, id)
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
                        liveData.value = beans
                    }
                }
            }
        }
    }

}