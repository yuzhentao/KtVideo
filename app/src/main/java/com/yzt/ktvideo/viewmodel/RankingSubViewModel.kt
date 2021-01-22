package com.yzt.ktvideo.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yzt.bean.RankingSubBean
import com.yzt.ktvideo.repository.RankingSubRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class RankingSubViewModel : ViewModel() {

    val liveData: MutableLiveData<MutableList<RankingSubBean.Item.Data.Content.DataX>> by lazy {
        MutableLiveData<MutableList<RankingSubBean.Item.Data.Content.DataX>>()
    }

    fun load(context: Context?, strategy: String) {
        context?.let {
            RankingSubRepository.loadData(context, strategy)
        }
            ?.flatMap { t ->
                val beans: MutableList<RankingSubBean.Item.Data.Content.DataX> = mutableListOf()
                for (item in t.itemList) {
                    item.data?.content?.data?.let {
                        beans.add(it)
                    }
                }
                Observable.just(beans)
            }
            ?.subscribe(object : Observer<MutableList<RankingSubBean.Item.Data.Content.DataX>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: MutableList<RankingSubBean.Item.Data.Content.DataX>) {
                    liveData.value = t
                }

                override fun onError(e: Throwable) {

                }
            })
    }

}