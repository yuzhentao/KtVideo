package com.yzt.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.yzt.common.R
import com.yzt.common.base.App
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

@Suppress("UNCHECKED_CAST")
fun <V : View> Activity.bindView(id: Int): Lazy<V> = lazy {
    viewFinder(id) as V
}

private val viewFinder: Activity.(Int) -> View?
    get() = {
        findViewById(it)
    }

/**
 * 相当于
 * fun Context.getColor(resId: Int): Int {
 * return ContextCompat.getColor(this, resId)
 * }
 */
fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)

fun Context.drawable(resId: Int) = ContextCompat.getDrawable(this, resId)

fun Context.dimensionPixelOffset(@DimenRes resId: Int) =
    this.resources.getDimensionPixelOffset(resId)

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

fun <T> Observable<T>.ioMain(): Observable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}