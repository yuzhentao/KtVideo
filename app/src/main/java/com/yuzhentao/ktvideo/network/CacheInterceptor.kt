package com.yuzhentao.ktvideo.network

import android.content.Context
import com.yuzhentao.ktvideo.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        return if (NetworkUtils.isNetConnected(context)) {
            val response = chain.proceed(request)
            val maxAge = 60
            response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=$maxAge").build()
        } else {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            val maxStale = 60 * 60 * 24 * 3
            response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, only-if-cached, max-stale=$maxStale").build()
        }
    }

}