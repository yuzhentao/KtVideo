package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.bean.SearchBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import io.reactivex.Observable

/**
 * 搜索
 *
 * @author yzt 2021/2/9
 */
class SearchModel {

    fun loadData(context: Context, key: String): Observable<SearchBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getSearch(key, AppUtil.getOSModel())!!.ioMain()
    }

}