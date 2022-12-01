package com.yzt.home.repository

import android.content.Context
import com.yzt.bean.HomeBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient

/**
 * 首页
 *
 * @author yzt 2022/12/1
 */
object HomeRepository {

    //    fun loadData(context: Context, isFirst: Boolean, date: String?): Observable<HomeBean>? {
//        val apiService = RetrofitClient.getInstance(context, ApiService.BASE_URL).create(ApiService::class.java)
//        return when (isFirst) {
//            true -> apiService?.getHomeData()!!.ioMain()
//            false -> apiService?.getHomeMoreData(date.toString())?.ioMain()
//        }
//    }

    suspend fun loadDataByCoroutine(context: Context, isFirst: Boolean, date: String?): HomeBean? {
        val apiService =
            RetrofitClient.getInstance(context, ApiService.BASE_URL).create(ApiService::class.java)
        return when (isFirst) {
            true -> apiService?.getHomeDataByCoroutine()
            false -> apiService?.getHomeMoreDataByCoroutine(date.toString())
        }
    }

}