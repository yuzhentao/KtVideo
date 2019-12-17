package com.yuzhentao.ktvideo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.yuzhentao.ktvideo.app.App

/**
 * 网络工具
 */
object NetworkUtil {

    private fun getConnectivityManager(): ConnectivityManager? {
        return App.app!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isConnected(): Boolean {
        val cm = getConnectivityManager()
        cm?.let {
            return if (Build.VERSION.SDK_INT > 22) {
                val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                //NetworkCapabilities.NET_CAPABILITY_VALIDATED：需要认证或者不能联通的wifi这个判断会为false，所以还是要慎用
                (networkCapabilities != null
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        /*&& networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)*/)
            } else {
                val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                activeNetwork?.isConnected == true
            }
        }
        return false
    }

    /**
     * 是否是无线局域网
     */
    fun isWifi(): Boolean {
        if (isConnected()) {
            val cm = getConnectivityManager()
            cm?.let {
                return if (Build.VERSION.SDK_INT > 22) {
                    val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                } else {
                    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                    activeNetwork?.type == ConnectivityManager.TYPE_WIFI
                }
            }
        }
        return false
    }

    /**
     * 是否是蜂窝移动网络
     */
    fun isCellular(): Boolean {
        if (isConnected()) {
            val cm = getConnectivityManager()
            cm?.let {
                return if (Build.VERSION.SDK_INT > 22) {
                    val networkCapabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                } else {
                    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                    activeNetwork?.type == ConnectivityManager.TYPE_MOBILE
                }
            }
        }
        return false
    }

}