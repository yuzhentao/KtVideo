package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class HomeModel {

    fun loadData(context: Context, isFirst: Boolean, data: String?): Observable<HomeBean>? {
        val apiService = RetrofitClient.getInstance(context, ApiService.BASE_URL).create(ApiService::class.java)
        return when (isFirst) {
            true -> apiService?.getHomeData()
            false -> apiService?.getHomeMoreData(data.toString(), "2")
        }
    }

}