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

    /**
     * 闪屏页
     */
    @GET("v2/configs?${Constant.PARAMS}")
    fun getSplashData(): Observable<SplashBean>

    /**
     * 首页第一页
     */
    @GET("v2/feed?${Constant.PARAMS}&num=2")
    fun getHomeData(): Observable<HomeBean>

    /**
     * 首页更多
     */
    @GET("v2/feed")
    fun getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>

    /**
     * 发现
     */
    @GET("v2/categories?${Constant.PARAMS}")
    fun getDiscoverData(): Observable<MutableList<DiscoverBean>>

    /**
     * 分类详情
     */
    @GET("v6/tag/index?${Constant.PARAMS}")
    fun getDiscoverDetailData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailBean>

    /**
     * 分类详情-推荐
     */
    @GET("v1/tag/videos?${Constant.PARAMS}")
    fun getDiscoverDetailLeftData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailLeftBean>

    /**
     * 分类详情-广场
     */
    @GET("v6/tag/dynamics?${Constant.PARAMS}")
    fun getDiscoverDetailRightData(@Query("id") id: String, @Query("deviceModel") deviceModel: String): Observable<DiscoverDetailRightBean>

    /**
     * 热门排行
     */
    @GET("v3/ranklist")
    fun getHotData(@Query("num") num: Int, @Query("strategy") strategy: String, @Query("udid") udid: String, @Query("vc") vc: Int): Observable<HotBean>

    /**
     * 热门搜索
     */
    @GET("v3/queries/hot?${Constant.PARAMS}")
    fun getHotSearch(): Observable<MutableList<String>>

    /**
     * 搜索
     */
    @GET("v3/search?${Constant.PARAMS}")
    fun getSearch(@Query("query") key: String, @Query("deviceModel") deviceModel: String): Observable<SearchBean>

}