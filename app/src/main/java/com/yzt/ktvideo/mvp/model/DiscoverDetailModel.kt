package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.ktvideo.bean.DiscoverDetailBean
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import com.yzt.ktvideo.util.AppUtil
import io.reactivex.Observable

class DiscoverDetailModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailData(id, AppUtil.getOSModel())!!.ioMain()
    }

}