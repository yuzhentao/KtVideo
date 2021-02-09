package com.yzt.discover.mvp.model

import android.content.Context
import com.yzt.bean.DiscoverDetailRightBean
import com.yzt.common.extension.ioMain
import com.yzt.common.util.AppUtil
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

/**
 * 发现详情-广场
 *
 * @author yzt 2021/2/9
 */
class DiscoverDetailRightModel {

    fun loadData(context: Context, id: String): Observable<DiscoverDetailRightBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getDiscoverDetailRightData(id, AppUtil.getOSModel())!!.ioMain()
    }

}