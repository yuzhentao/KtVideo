package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class DiscoverDetailLeftModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailLeftBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailLeftData(id, AppUtil.getOSModel())!!.ioMain()
    }

}