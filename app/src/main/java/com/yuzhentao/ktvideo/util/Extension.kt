package com.yuzhentao.ktvideo.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.app.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

inline fun <reified T : Activity> Activity.newIntent(isFinish: Boolean) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
    if (isFinish) {
        finish()
    }
}

fun Context.shortToast(message: String): Toast {
    val toast = Toast(App.app)
    val view = View.inflate(App.app, R.layout.layout_toast, null)
    val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
    tv.text = message
    toast.view = view
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
}

fun Context.longToast(message: String): Toast {
    val toast = Toast(App.app)
    val view = View.inflate(App.app, R.layout.layout_toast, null)
    val tv = view!!.findViewById<AppCompatTextView>(R.id.tv)
    tv.text = message
    toast.view = view
    toast.duration = Toast.LENGTH_LONG
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
}

fun <T> Observable<T>.normalSchedulers(): Observable<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}