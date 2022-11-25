package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverDetailBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 发现详情
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailModel {

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