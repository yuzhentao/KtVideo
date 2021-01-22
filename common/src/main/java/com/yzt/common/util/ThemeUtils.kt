package com.yzt.common.util

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.yzt.common.R

object ThemeUtils {

    fun getThemePrimaryColor(context: Context): Int {
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
                typedValue.data,
                intArrayOf(R.attr.colorPrimary)
        )
        val color = a.getColor(0, Color.BLUE)
        a.recycle()
        return color
    }

    fun getThemeAccentColor(context: Context): Int {
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
                typedValue.data,
                intArrayOf(R.attr.colorAccent)
        )
        val color = a.getColor(0, Color.CYAN)
        a.recycle()
        return color
    }

}