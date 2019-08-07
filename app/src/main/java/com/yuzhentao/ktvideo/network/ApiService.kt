package com.yuzhentao.ktvideo.network

import com.yuzhentao.ktvideo.bean.DiscoverBean
import com.yuzhentao.ktvideo.bean.DiscoverDetailBean
import com.yuzhentao.ktvideo.bean.HomeBean
import com.yuzhentao.ktvideo.bean.HotBean
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
    @GET("v2/feed?num=2&udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    //获取发现的数据
    @GET("v2/categories?udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524")
    fun getDiscoverData(): Observable<MutableList<DiscoverBean>>

    @GET("v6/tag/index?udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524")
    fun getDiscoverDetailData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailBean>

    //获取热门排行信息
    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String, @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

}