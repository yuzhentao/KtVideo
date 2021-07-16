package com.yzt.ranking.mvp.model

import android.content.Context
import com.yzt.bean.RankingBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import io.reactivex.Observable

/**
 * 排行
 *
 * @author yzt 2021/2/9
 */
class RankingModel {

    fun loadData(context: Context): Observable<RankingBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getRankingData(AppUtil.getOSModel())!!.ioMain()
    }

}