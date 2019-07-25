package com.yuzhentao.ktvideo.util

import android.content.Context
import android.support.annotation.DimenRes


class ResourcesUtil {

    companion object {
        fun getDimensionPixelOffset(context: Context, @DimenRes resId: Int): Int {
            return context.resources.getDimensionPixelOffset(resId)
        }
    }

}