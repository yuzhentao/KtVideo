package com.yzt.common.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.yzt.common.base.App

/**
 * 网络工具
 */
object NetworkUtil {

    private const val NETWORK_NONE = "" //无网络连接
    private const val NETWORK_WIFI = "wifi" //无线局域网
    private const val NETWORK_2G = "2g" //2G
    private const val NETWORK_3G = "3g" //3G
    private const val NETWORK_4G = "4g" //4G
    private const val NETWORK_MOBILE = "" //蜂窝移动网络

    private fun getConnectivityManager(): ConnectivityManager? {
        return App.app!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    }

    /**
     * 网络是否连接
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isConnected(): Boolean {
        val cm = getConnectivityManager()
        cm?.let {
            return if (Build.VERSION.SDK_INT > 22) {
                val networkCapabilities = it.getNetworkCapabilities(it.activeNetwork)
                //NetworkCapabilities.NET_CAPABILITY_VALIDATED：需要认证或者不能联通的wifi这个判断会为false，所以还是要慎用
                (networkCapabilities != null
                        && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        /*&& networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)*/)
            } else {
                val activeNetwork: NetworkInfo? = it.activeNetworkInfo
                activeNetwork?.isConnected == true
            }
        }
        return false
    }

    /**
     * 是否是无线局域网
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isWifi(): Boolean {
        if (isConnected()) {
            val cm = getConnectivityManager()
            cm?.let {
                return if (Build.VERSION.SDK_INT > 22) {
                    val networkCapabilities = it.getNetworkCapabilities(it.activeNetwork)
                    networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        ?: false
                } else {
                    val activeNetwork: NetworkInfo? = it.activeNetworkInfo
                    activeNetwork?.type == ConnectivityManager.TYPE_WIFI
                }
            }
        }
        return false
    }

    /**
     * 是否是蜂窝移动网络
     */
    @SuppressLint("ObsoleteSdkInt")
    fun isCellular(): Boolean {
        if (isConnected()) {
            val cm = getConnectivityManager()
            cm?.let {
                return if (Build.VERSION.SDK_INT > 22) {
                    val networkCapabilities = it.getNetworkCapabilities(it.activeNetwork)
                    networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        ?: false
                } else {
                    val activeNetwork: NetworkInfo? = it.activeNetworkInfo
                    activeNetwork?.type == ConnectivityManager.TYPE_MOBILE
                }
            }
        }
        return false
    }

    /**
     * 获取运营商名字
     */
    fun getOperatorName(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simOperatorName
    }

    /**
     * 获取网络连接类型
     */
    fun getNetworkState(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return NETWORK_NONE
        val info = cm.activeNetworkInfo
        if (info == null || !info.isAvailable) {
            return NETWORK_NONE
        }
        //无线局域网
        val wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null) {
            val state = wifiInfo.state
            if (state != null) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI
                }
            }
        }
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            when (tm.networkType) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> NETWORK_2G
                TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> NETWORK_3G
                TelephonyManager.NETWORK_TYPE_LTE -> NETWORK_4G
                else -> NETWORK_MOBILE
            }
        } else {
            NETWORK_MOBILE
        }
    }

}