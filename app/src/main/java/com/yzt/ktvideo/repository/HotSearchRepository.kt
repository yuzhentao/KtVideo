package com.yzt.ktvideo.repository

import android.content.Context
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 热门搜索词
 *
 * @author yzt 2022/12/1
 */
object HotSearchRepository {

//    fun loadData(context: Context): Observable<MutableList<String>>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getHotSearch(AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context): MutableList<String>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getHotSearchByCoroutine(AppUtil.getOSModel())
    }

}