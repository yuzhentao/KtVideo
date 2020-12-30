package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.common.util.AppUtil
import com.yzt.ktvideo.bean.SearchBean
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class SearchModel {

    fun loadData(context: Context, key: String): Observable<SearchBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSearch(key, AppUtil.getOSModel())!!.ioMain()
    }

}