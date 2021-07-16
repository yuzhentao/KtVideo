package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverDetailBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import io.reactivex.Observable

/**
 * 发现详情
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailData(id, AppUtil.getOSModel())!!.ioMain()
    }

}