package com.yzt.discover.repository

import android.content.Context
import com.yzt.bean.DiscoverDetailBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 发现-详情
 *
 * @author yzt 2022/12/1
 */
object DiscoverDetailRepository {

//    fun loadData(context: Context, id: String): Observable<DiscoverDetailBean>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getDiscoverDetailData(id, AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context, id: String): DiscoverDetailBean? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailDataByCoroutine(id, AppUtil.getOSModel())
    }

}