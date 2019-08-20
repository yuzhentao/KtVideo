package com.yuzhentao.ktvideo.mvp.model

import android.content.Context
import com.yuzhentao.ktvideo.bean.RankingBean
import com.yuzhentao.ktvideo.network.ApiService
import com.yuzhentao.ktvideo.network.RetrofitClient
import com.yuzhentao.ktvideo.util.AppUtil
import io.reactivex.Observable

class RankingModel {

    fun loadData(context: Context): Observable<RankingBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingData(AppUtil.getOSModel())
    }

}