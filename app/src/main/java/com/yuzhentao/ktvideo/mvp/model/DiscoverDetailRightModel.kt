package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.DiscoverDetailRightBean
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import com.yuzhentao.ktvideo.util.AppUtil
import io.reactivex.Observable

class DiscoverDetailRightModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailRightBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailRightData(id, AppUtil.getOSModel())
    }

}