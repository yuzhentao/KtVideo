package com.yuzhentao.ktvideo.util

import android.view.View
import android.view.ViewGroup

class ViewUtil {

    companion object {
        fun setMargins(view: View?, l: Int, t: Int, r: Int, b: Int) {
            view?.let {
                if (view.layoutParams != null && view.layoutParams is ViewGroup.MarginLayoutParams) {
                    val mlp = view.layoutParams as ViewGroup.MarginLayoutParams
                    if (AppUtil.isRtl()) {
                        mlp.setMargins(r, t, l, b)
                    } else {
                        mlp.setMargins(l, t, r, b)
                    }
                    view.requestLayout()
                }
            }
        }

        fun setPaddings(view: View?, l: Int, t: Int, r: Int, b: Int) {
            view?.let {
                if (AppUtil.isRtl()) {
                    view.setPadding(r, t, l, b)
                } else {
                    view.setPadding(l, t, r, b)
                }
            }
        }
    }

}