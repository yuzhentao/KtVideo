package com.yzt.discover.repository

import android.content.Context
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 发现-详情-广场
 *
 * @author yzt 2022/12/1
 */
object DiscoverDetailRightRepository {

//    fun loadData(context: Context, id: String): Observable<DiscoverDetailRightBean>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getDiscoverDetailRightData(id, AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context, id: String): DiscoverDetailRightBean? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailRightDataByCoroutine(id, AppUtil.getOSModel())
    }

}