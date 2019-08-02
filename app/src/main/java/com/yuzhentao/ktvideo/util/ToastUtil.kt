package com.yuzhentao.ktvideo.util

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.app.App
import java.lang.reflect.Field


object ToastUtil {

    private var sField_TN: Field? = null
    private var sField_TN_Handler: Field? = null
    private var toast: Toast? = null

    init {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {//7.0或7.1
            try {
                sField_TN = Toast::class.java.getDeclaredField("mTN")
                sField_TN!!.setAccessible(true)
                sField_TN_Handler = sField_TN!!.getType().getDeclaredField("mHandler")
                sField_TN_Handler!!.setAccessible(true)
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

    @SuppressLint("ShowToast")
    fun shortTop(text: String) {
        if (toast != null) {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, text, Toast.LENGTH_SHORT)
        }
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortTop(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_SHORT)
        }
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortCenter(text: String) {
        if (toast != null) {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, text, Toast.LENGTH_SHORT)
        }
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortCenter(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_SHORT)
        }
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortBottom(text: String) {
        if (toast != null) {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, text, Toast.LENGTH_SHORT)
        }
        //        toast.setGravity(Gravity.BOTTOM, 0, 0);
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortBottom(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_SHORT)
        }
        //        toast.setGravity(Gravity.BOTTOM, 0, 0);
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun longTop(text: String) {
        if (toast != null) {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast = Toast.makeText(App.app, text, Toast.LENGTH_LONG)
        }
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun longTop(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_LONG)
        }
        toast!!.setGravity(Gravity.TOP, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun longCenter(text: String) {
         if (toast != null) {

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

    @SuppressLint("ShowToast")
    fun longCenter(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_LONG)
        }
        toast!!.setGravity(Gravity.CENTER, 0, 0)
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun longBottom(text: String) {
        if (toast != null) {
            toast!!.setText(text)
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast = Toast.makeText(App.app, text, Toast.LENGTH_LONG)
        }
        //        toast.setGravity(Gravity.BOTTOM, 0, 0);
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun longBottom(@StringRes resId: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_LONG)
        }
        //        toast.setGravity(Gravity.BOTTOM, 0, 0);
        hook(toast)
        toast!!.show()
    }

    @SuppressLint("ShowToast")
    fun shortBottomOffset(@StringRes resId: Int, xOffset: Int, yOffset: Int) {
        if (toast != null) {
            toast!!.setText(resId)
            toast!!.duration = Toast.LENGTH_SHORT
        } else {
            toast = Toast.makeText(App.app, resId, Toast.LENGTH_SHORT)
        }
        toast!!.setGravity(Gravity.BOTTOM, xOffset, yOffset)
        hook(toast)
        toast!!.show()
    }

}