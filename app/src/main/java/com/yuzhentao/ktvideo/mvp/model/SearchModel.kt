package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.SearchBean
import com.yuzhentao.ktvideo.extension.ioMain
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import com.yuzhentao.ktvideo.util.AppUtil
import io.reactivex.Observable

class SearchModel {

    fun loadData(context: Context, key: String): Observable<SearchBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSearch(key, AppUtil.getOSModel())!!.ioMain()
    }

}