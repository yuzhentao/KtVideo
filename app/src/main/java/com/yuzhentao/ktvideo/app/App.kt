package com.yuzhentao.ktvideo.app

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.zhouyou.http.EasyHttp
import timber.log.Timber

class App : MultiDexApplication() {

    companion object {
        var app: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initTimber()
        initRxEasyHttp()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(app)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRxEasyHttp() {
        EasyHttp.init(app)
    }

}