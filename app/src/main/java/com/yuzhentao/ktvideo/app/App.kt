package com.yuzhentao.ktvideo.app

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import timber.log.Timber

class App : MultiDexApplication() {

    companion object {
        var app: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initTimber()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}