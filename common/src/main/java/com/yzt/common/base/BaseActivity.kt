package com.yzt.common.base

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.yzt.common.receiver.NetworkReceiver
import io.reactivex.disposables.CompositeDisposable

/**
 * @author yzt 2020/12/31
 */
abstract class BaseActivity : Activity() {

    protected var context: Context? = null
    protected var activity: BaseActivity? = null

    private var networkReceiver: NetworkReceiver? = null
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activity = this
        compositeDisposable = CompositeDisposable()
        initBeforeSetLayout(savedInstanceState)
        setLayoutId()?.let {
            setContentView(it)
        }
        setLayoutView()?.let {
            setContentView(it)
        }
        networkReceiver = NetworkReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, intentFilter)
        initAfterSetLayout(savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {

        }
        super.onDestroy()
        context = null
        activity = null
        compositeDisposable = null
    }

    open fun isActivityExist(): Boolean {
        return activity != null && !activity!!.isFinishing && context != null
    }

    protected open fun hasPermission(vararg permissions: String?): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission!!
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    protected abstract fun initBeforeSetLayout(savedInstanceState: Bundle?)

    protected abstract fun setLayoutId(): Int?

    protected abstract fun setLayoutView(): View?

    protected abstract fun initAfterSetLayout(savedInstanceState: Bundle?)

    protected abstract fun initView(savedInstanceState: Bundle?)

    protected abstract fun initData(savedInstanceState: Bundle?)

}