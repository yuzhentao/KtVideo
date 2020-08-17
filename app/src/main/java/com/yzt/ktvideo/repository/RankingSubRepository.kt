package com.yzt.ktvideo.repository

import android.content.Context
import com.yzt.ktvideo.bean.RankingSubBean
import com.yzt.ktvideo.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import com.yzt.ktvideo.util.AppUtil
import io.reactivex.Observable

object RankingSubRepository {

    fun loadData(context: Context, strategy: String): Observable<RankingSubBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingSubData(strategy, AppUtil.getOSModel())!!.ioMain()
    }

}