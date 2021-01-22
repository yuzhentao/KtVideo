package com.yzt.ktvideo.mvp.model

import android.content.Context
import com.yzt.common.util.AppUtil
import com.yzt.bean.VideoRelatedBean
import com.yzt.common.extension.ioMain
import com.yzt.ktvideo.network.ApiService
import com.yzt.ktvideo.network.RetrofitClient
import io.reactivex.Observable

class VideoRelatedModel {

    fun loadData(context: Context, id: String?): Observable<VideoRelatedBean>? {
        val retrofitClient = RetrofitClient.getInstance(context, ApiService.BASE_URL)
        val apiService = retrofitClient.create(ApiService::class.java)
        return apiService?.getVideoRelatedData(id!!, AppUtil.getOSModel())!!.ioMain()
    }

}