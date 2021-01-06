package com.yzt.common.base

import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yzt.common.listener.LifecycleListener
import com.yzt.common.receiver.NetworkReceiver
import com.yzt.transitionhelper.TransitionsHelper
import io.reactivex.disposables.CompositeDisposable

/**
 * @author yzt 2020/12/31
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var context: Context? = null
    protected var activity: BaseActivity? = null

    protected val rxPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }

    private var networkReceiver: NetworkReceiver? = null
    private var lifecycleListener: LifecycleListener? = null
    var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        activity = this
        compositeDisposable = CompositeDisposable()
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
        init(savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        TransitionsHelper.unbind(this)
        if (lifecycleListener != null) {
            lifecycleListener!!.onDestroy()
            lifecycleListener = null
        }
        try {
            unregisterReceiver(networkReceiver)
        } catch (e: Exception) {

        }
        super.onDestroy()
    }

    open fun setOnLifecycleListener(lifecycleListener: LifecycleListener?) {
        this.lifecycleListener = lifecycleListener
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

    protected abstract fun setLayoutId(): Int?

    protected abstract fun setLayoutView(): View?

    protected abstract fun init(savedInstanceState: Bundle?)

    protected abstract fun initView(savedInstanceState: Bundle?)

    protected abstract fun initData(savedInstanceState: Bundle?)

}