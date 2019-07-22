package com.yuzhentao.ktvideo.network

import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        //伴生对象
        val BASE_URL: String
            get() = "http://baobab.kaiyanapp.com/api/"
    }

    //获取首页第一页数据
    @GET("v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    //获取发现的数据
    @GET("v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83")
    fun getDiscoverData(): Observable<MutableList<DiscoverBean>>

    @GET("v4/categories/detail/tab")
    fun getDiscoverDetailData(@Query("id") id: String): Observable<DiscoverDetailBean>

}