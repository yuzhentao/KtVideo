package com.yzt.ranking.repository

import android.content.Context
import com.yzt.bean.RankingBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 排行
 *
 * @author yzt 2022/11/30
 */
object RankingRepository {

    //    fun loadData(context: Context): Observable<RankingBean>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getRankingData(AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context): RankingBean? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingDataByCoroutine(AppUtil.getOSModel())
    }

}