package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.bean.VideoRelatedBean
import com.yzt.common.network.ApiService
import com.yzt.common.network.RetrofitClient
import com.yzt.common.util.AppUtil

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2021/2/9
 */
class VideoRelatedModel {

//    fun loadData(context: Context, id: String?): Observable<VideoRelatedBean>? {
//        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
//        val apiService = retrofitClient.create(ApiService::class.java)
//        return apiService?.getVideoRelatedData(id!!, AppUtil.getOSModel())?.ioMain()
//    }

    suspend fun loadDataByCoroutine(context: Context, id: String?): VideoRelatedBean? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getVideoRelatedDataByCoroutine(id!!, AppUtil.getOSModel())
    }

}