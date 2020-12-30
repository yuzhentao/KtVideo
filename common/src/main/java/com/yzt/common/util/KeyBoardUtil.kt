package com.yzt.common.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object KeyBoardUtil {

    fun openKeyboard(context: Context?, et: EditText) {
        context?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
            imm.showSoftInput(et, InputMethodManager.RESULT_SHOWN)
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    fun closeKeyboard(context: Context?, et: EditText) {
        context?.let {
            val imm = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(et.windowToken, 0)
        }
    }

}