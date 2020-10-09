package com.yzt.ktvideo.util

import android.view.View
import android.view.ViewGroup

class ViewUtil {

    companion object {
        fun setMargins(view: View?, l: Int, t: Int, r: Int, b: Int) {
            view?.let {
                if (it.layoutParams != null && it.layoutParams is ViewGroup.MarginLayoutParams) {
                    val mlp = it.layoutParams as ViewGroup.MarginLayoutParams
                    if (AppUtil.isRtl()) {
                        mlp.setMargins(r, t, l, b)
                    } else {
                        mlp.setMargins(l, t, r, b)
                    }
                    it.requestLayout()
                }
            }
        }

        fun setPaddings(view: View?, l: Int, t: Int, r: Int, b: Int) {
            view?.let {
                if (AppUtil.isRtl()) {
                    it.setPadding(r, t, l, b)
                } else {
                    it.setPadding(l, t, r, b)
                }
            }
        }
    }

}