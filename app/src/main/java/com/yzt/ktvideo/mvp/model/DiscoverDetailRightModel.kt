package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.ktvideo.bean.DiscoverDetailRightBean
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import com.yzt.ktvideo.util.AppUtil
import io.reactivex.Observable

class DiscoverDetailRightModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailRightBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailRightData(id, AppUtil.getOSModel())!!.ioMain()
    }

}