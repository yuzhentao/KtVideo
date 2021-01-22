package com.yzt.ktvideo.network

import android.content.Context

object RetrofitManager {

    fun getApiService(context: Context): ApiService {
        return RetrofitClient.getInstance(context, ApiService.BASE_URL)
            .create(ApiService::class.java)!!
    }

}