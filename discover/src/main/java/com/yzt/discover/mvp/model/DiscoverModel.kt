package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class DiscoverModel {

    fun loadData(context: Context): Observable<DiscoverBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverData(AppUtil.getOSModel())!!.ioMain()
    }

}