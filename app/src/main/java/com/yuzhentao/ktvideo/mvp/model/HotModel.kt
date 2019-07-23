package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.HotBean
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class HotModel {

    fun loadData(context: Context, strategy: String?): Observable<HotBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHotData(10, strategy!!, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }

}