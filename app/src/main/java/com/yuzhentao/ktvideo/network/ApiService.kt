package com.yuzhentao.ktvideo.network

import com.yuzhentao.ktvideo.bean.*
import com.yuzhentao.ktvideo.key.Constant
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
    @GET("v2/feed?${Constant.PARAMS}&num=2")
    fun getHomeData(): Observable<HomeBean>

    //获取首页第一页之后的数据
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    //获取发现的数据
    @GET("v2/categories?${Constant.PARAMS}")
    fun getDiscoverData(): Observable<MutableList<DiscoverBean>>

    /**
     * 获取分类详情
     */
    @GET("v6/tag/index?${Constant.PARAMS}")
    fun getDiscoverDetailData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailBean>

    /**
     * 获取分类详情-推荐
     */
    @GET("v1/tag/videos?${Constant.PARAMS}")
    fun getDiscoverDetailLeftData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailLeftBean>

    /**
     * 获取分类详情-广场
     */
    @GET("v6/tag/dynamics?${Constant.PARAMS}")
    fun getDiscoverDetailRightData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailRightBean>

    //获取热门排行信息
    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String, @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

}