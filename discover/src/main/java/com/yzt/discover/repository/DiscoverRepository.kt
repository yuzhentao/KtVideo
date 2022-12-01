package com.yzt.discover.repository

import android.content.Context
import com.yzt.bean.DiscoverBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 发现
 *
 * @author yzt 2022/12/1
 */
object DiscoverRepository {

//    fun loadData(context: Context): Observable<DiscoverBean>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getDiscoverData(AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context): DiscoverBean? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDataByCoroutine(AppUtil.getOSModel())
    }

}