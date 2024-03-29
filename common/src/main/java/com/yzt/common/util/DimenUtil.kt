package com.yzt.common.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Display
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.yzt.common.base.App

object DimenUtil {

    fun getWidthInPxHasNav(): Float {
        return App.app!!.resources.displayMetrics.widthPixels.toFloat()
    }

    fun getHeightInPxHasNav(): Float {
        return App.app!!.resources.displayMetrics.heightPixels.toFloat()
    }

    fun getWidthInPx(context: Context): Float {
        val dm = DisplayMetrics()
        return if (getDisplay(context) != null) {
            getDisplay(context)?.getRealMetrics(dm)
            dm.widthPixels.toFloat()
        } else {
            0F
        }
    }

    fun getHeightInPx(context: Context): Float {
        val dm = DisplayMetrics()
        return if (getDisplay(context) != null) {
            getDisplay(context)?.getRealMetrics(dm)
            dm.heightPixels.toFloat()
        } else {
            0F
        }
    }

    /**
     * px转dp
     */
    fun px2dp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5f).toInt()
    }

    /**
     * dp转px
     */
    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    /**
     * px转sp
     */
    fun px2sp(context: Context, px: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (px / scale + 0.5f).toInt()
    }

    /**
     * sp转px
     */
    fun sp2px(context: Context, sp: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (sp * scale + 0.5f).toInt()
    }

    /**
     * 使用TypedValue类将dp转px
     */
    fun dp2px(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 使用TypedValue类将sp转px
     */
    fun sp2px(context: Context, sp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    private fun getDisplay(context: Context): Display? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getDisplayR(context)
        } else {
            getDisplayL()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private fun getDisplayR(context: Context): Display? {
        return context.display
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun getDisplayL(): Display? {
        val wm = App.app!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        return wm?.defaultDisplay
    }

}