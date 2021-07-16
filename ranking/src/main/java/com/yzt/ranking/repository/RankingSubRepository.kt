package com.yzt.ranking.repository

import android.content.Context
import com.yzt.bean.RankingSubBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import io.reactivex.Observable

/**
 * 排行-子Fragment
 *
 * @author yzt 2021/2/9
 */
object RankingSubRepository {

    fun loadData(context: Context, strategy: String): Observable<RankingSubBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingSubData(strategy, AppUtil.getOSModel())!!.ioMain()
    }

}