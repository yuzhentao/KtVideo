package com.yuzhentao.ktvideo.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.yuzhentao.ktvideo.app.App

class DimenUtil {

    companion object {
        fun getWidthInPxHasNav(): Float {
            return App.app!!.resources.displayMetrics.widthPixels.toFloat()
        }

        fun getHeightInPxHasNav(): Float {
            return App.app!!.resources.displayMetrics.heightPixels.toFloat()
        }

        fun getWidthInPx(): Float {
            val dm = DisplayMetrics()
            val wm: WindowManager? = App.app!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return if (wm != null) {
                wm.defaultDisplay.getRealMetrics(dm)
                dm.widthPixels.toFloat()
            } else {
                0F
            }
        }

        fun getHeightInPx(): Float {
            val dm = DisplayMetrics()
            val wm: WindowManager? = App.app!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            return if (wm != null) {
                wm.defaultDisplay.getRealMetrics(dm)
                dm.heightPixels.toFloat()
            } else {
                0F
            }
        }
    }

}