package com.yzt.common.util

import android.graphics.Paint
import android.widget.TextView

class TextViewUtil {

    companion object {
        fun setBold(tv: TextView?, isBold: Boolean) {
            tv?.let {
                val paint: Paint? = it.paint
                paint?.let { itt ->
                    itt.isFakeBoldText = isBold
                }
                tv.postInvalidate()
            }
        }
    }

}