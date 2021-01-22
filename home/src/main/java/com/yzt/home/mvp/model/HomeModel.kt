package com.yzt.home.mvp.model

import android.content.Context
import com.yzt.bean.HomeBean
import com.yzt.common.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class HomeModel {

    fun loadData(context: Context, isFirst: Boolean, date: String?): Observable<HomeBean>? {
        val apiService = RetrofitClient.getInstance(context, ApiService.BASE_URL).create(ApiService::class.java)
        return when (isFirst) {
            true -> apiService?.getHomeData()!!.ioMain()
            false -> apiService?.getHomeMoreData(date.toString())!!.ioMain()
        }
    }

}