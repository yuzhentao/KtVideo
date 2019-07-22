package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class DiscoverDetailModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailData(id)
    }

}