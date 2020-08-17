package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.ktvideo.bean.SplashBean
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import com.yzt.ktvideo.util.AppUtil
import io.reactivex.Observable

class SplashModel {

    fun loadData(context: Context): Observable<SplashBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSplashData(AppUtil.getOSModel())!!.ioMain()
    }

}