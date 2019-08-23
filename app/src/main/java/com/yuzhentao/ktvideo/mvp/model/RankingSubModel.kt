package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.RankingSubBean
import com.yuzhentao.ktvideo.extension.ioMain
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import com.yuzhentao.ktvideo.util.AppUtil
import io.reactivex.Observable

class RankingSubModel {

    fun loadData(context: Context, strategy: String): Observable<RankingSubBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingSubData(strategy, AppUtil.getOSModel())!!.ioMain()
    }

}