package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.bean.SplashBean
import com.yzt.common.extension.ioMain
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil
import io.reactivex.Observable

/**
 * 闪屏
 *
 * @author yzt 2021/2/9
 */
class SplashModel {

    fun loadData(context: Context): Observable<SplashBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSplashData(AppUtil.getOSModel())?.ioMain()
    }

}