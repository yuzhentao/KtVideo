package com.yuzhentao.ktvideo.util

import android.os.Build
import android.text.TextUtils
import android.view.View
import java.util.*

object AppUtil {

    /**
     * 是否是RTL布局
     */
    fun isRtl(): Boolean {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL
    }

    /**
     * 获取手机型号
     */
    fun getOSModel(): String {
        return Build.MODEL
    }

}