package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverDetailLeftBean
import com.yzt.common.extension.ioMain
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil
import io.reactivex.Observable

/**
 * 发现详情-推荐
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailLeftModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailLeftBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailLeftData(id, AppUtil.getOSModel())?.ioMain()
    }

}