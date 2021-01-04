package com.yzt.common.util

import android.os.Build
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.yzt.common.R
import com.yzt.common.base.App
import java.lang.reflect.Field

object ToastUtil {

    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null
    private var toast: Toast? = null

    init {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {//7.0或7.1
            try {
                sField_TN = Toast::class.java.getDeclaredField("mTN")
                sField_TN!!.isAccessible = true
                sField_TN_Handler = sField_TN!!.getType().getDeclaredField("mHandler")
                sField_TN_Handler!!.isAccessible = true
            } catch (ignored: Exception) {

            }
        }
    }

    private fun hook(toast: Toast?) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            try {
                val tn = sField_TN!!.get(toast)
                val preHandler = sField_TN_Handler!!.get(tn) as Handler
                sField_TN_Handler!!.set(tn, SafelyHandlerWrapper(preHandler))
            } catch (ignored: Exception) {

            }
        }
    }

    private class SafelyHandlerWrapper internal constructor(private val impl: Handler) : Handler() {

        override fun dispatchMessage(msg: Message) {
            try {
                super.dispatchMessage(msg)
            } catch (ignored: Exception) {

            }

        }

        override fun handleMessage(msg: Message) {
            impl.handleMessage(msg)//需要委托给原Handler执行
        }

    }

    fun shortTop(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.TOP, 0, 0)//最高在状态栏之下
        hook(toast)
        toast!!.show()
    }

    fun shortTop(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun shortCenter(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun shortCenter(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun shortBottom(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)//最低在导航栏之上
        hook(toast)
        toast!!.show()
    }

    fun shortBottom(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_SHORT
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun longTop(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
    }

    fun longTop(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
    }

    fun longCenter(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun longCenter(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    fun longBottom(text: String) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.text = text
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)
        hook(toast)
    }

    fun longBottom(@StringRes resId: Int) {
        if (toast != null) {
            val tv = toast!!.view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
        } else {
            toast = Toast(App.app)
            val view = View.inflate(App.app, R.layout.layout_toast, null)
            val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
            tv.setText(resId)
            toast!!.view = view
        }
        toast!!.duration = Toast.LENGTH_LONG
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)
        hook(toast)
    }

}