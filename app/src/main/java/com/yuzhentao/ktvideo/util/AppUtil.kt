package com.yuzhentao.ktvideo.util

import android.text.TextUtils
import android.view.View
import java.util.*

class AppUtil {

    companion object {
        fun isRtl(): Boolean {
            return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL
        }
    }

}