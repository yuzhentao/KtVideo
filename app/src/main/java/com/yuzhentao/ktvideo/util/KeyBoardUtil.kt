package com.yuzhentao.ktvideo.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyBoardUtil {

    fun openKeyboard(context: Context?, et: EditText) {
        context?.let {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun closeKeyboard(context: Context?, et: EditText) {
        context?.let {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(et.windowToken, 0)
        }
    }

}