package com.yuzhentao.ktvideo.util

import android.graphics.Paint
import android.widget.TextView

class TextViewUtil {

    companion object {
        fun setBold(tv: TextView?, isBold: Boolean) {
            tv?.let {
                val paint: Paint? = tv.paint
                paint?.let {
                    paint.isFakeBoldText = isBold
                }
                tv.postInvalidate()
            }
        }
    }

}