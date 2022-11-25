package com.yzt.common.network

import com.yzt.bean.*
import com.yzt.common.key.Constant
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
//    @GET("v2/configs?${Constant.PARAMS}")
//    fun getSplashData(@Query("deviceModel") deviceModel: String): Observable<SplashBean>?

    /**
     * 闪屏页
     */
    @GET("v2/configs?${Constant.PARAMS}")
    suspend fun getSplashDataByCoroutine(@Query("deviceModel") deviceModel: String): SplashBean?

    /**
     * 首页
     */
//    @GET("v2/feed?${Constant.PARAMS}")
//    fun getHomeData(): Observable<HomeBean>?

    /**
     * 首页
     */
    @GET("v2/feed?${Constant.PARAMS}")
    suspend fun getHomeDataByCoroutine(): HomeBean?

    /**
     * 首页更多
     */
//    @GET("v2/feed")
//    fun getHomeMoreData(@Query("date") date: String): Observable<HomeBean>?

    /**
     * 首页更多
     */
    @GET("v2/feed")
    suspend fun getHomeMoreDataByCoroutine(@Query("date") date: String): HomeBean?

    /**
     * 视频相关推荐
     */
//    @GET("v4/video/related")
//    fun getVideoRelatedData(
//        @Query("id") id: String,
//        @Query("deviceModel") deviceModel: String
//    ): Observable<VideoRelatedBean>?

    /**
     * 视频相关推荐
     */
    @GET("v4/video/related")
    suspend fun getVideoRelatedDataByCoroutine(
        @Query("id") id: String,
        @Query("deviceModel") deviceModel: String
    ): VideoRelatedBean?

    /**
     * 分类
     */
//    @GET("v4/categories/all?${Constant.PARAMS}")
//    fun getDiscoverData(@Query("deviceModel") deviceModel: String): Observable<DiscoverBean>?

    /**
     * 分类
     */
    @GET("v4/categories/all?${Constant.PARAMS}")
    suspend fun getDiscoverDataByCoroutine(@Query("deviceModel") deviceModel: String): DiscoverBean?

    /**
     * 分类详情
     */
//    @GET("v6/tag/index?${Constant.PARAMS}")
//    fun getDiscoverDetailData(
//        @Query("id") id: String,
//        @Query("deviceModel") deviceModel: String
//    ): Observable<DiscoverDetailBean>?

    /**
     * 分类详情
     */
    @GET("v6/tag/index?${Constant.PARAMS}")
    suspend fun getDiscoverDetailDataByCoroutine(
        @Query("id") id: String,
        @Query("deviceModel") deviceModel: String
    ): DiscoverDetailBean?

    /**
     * 分类详情-推荐
     */
//    @GET("v1/tag/videos?${Constant.PARAMS}")
//    fun getDiscoverDetailLeftData(
//        @Query("id") id: String,
//        @Query("deviceModel") deviceModel: String
//    ): Observable<DiscoverDetailLeftBean>?

    /**
     * 分类详情-推荐
     */
    @GET("v1/tag/videos?${Constant.PARAMS}")
    suspend fun getDiscoverDetailLeftDataByCoroutine(
        @Query("id") id: String,
        @Query("deviceModel") deviceModel: String
    ): DiscoverDetailLeftBean?

    /**
     * 分类详情-广场
     */
//    @GET("v6/tag/dynamics?${Constant.PARAMS}")
//    fun getDiscoverDetailRightData(
//        @Query("id") id: String,
//        @Query("deviceModel") deviceModel: String
//    ): Observable<DiscoverDetailRightBean>?

    /**
     * 分类详情-广场
     */
    @GET("v6/tag/dynamics?${Constant.PARAMS}")
    suspend fun getDiscoverDetailRightDataByCoroutine(
        @Query("id") id: String,
        @Query("deviceModel") deviceModel: String
    ): DiscoverDetailRightBean?

    /**
     * 排行
     */
    @GET("v4/rankList?${Constant.PARAMS}")
    fun getRankingData(@Query("deviceModel") deviceModel: String): Observable<RankingBean>?

    /**
     * 排行子页
     */
    @GET("v4/rankList/videos?${Constant.PARAMS}")
    fun getRankingSubData(
        @Query("strategy") strategy: String,
        @Query("deviceModel") deviceModel: String
    ): Observable<RankingSubBean>?

    /**
     * 热门搜索
     */
//    @GET("v3/queries/hot?${Constant.PARAMS}")
//    fun getHotSearch(@Query("deviceModel") deviceModel: String): Observable<MutableList<String>>?

    /**
     * 热门搜索
     */
    @GET("v3/queries/hot?${Constant.PARAMS}")
    suspend fun getHotSearchByCoroutine(@Query("deviceModel") deviceModel: String): MutableList<String>?

    /**
     * 搜索
     */
    @GET("v3/search?${Constant.PARAMS}")
    fun getSearch(
        @Query("query") key: String,
        @Query("deviceModel") deviceModel: String
    ): Observable<SearchBean>?

}