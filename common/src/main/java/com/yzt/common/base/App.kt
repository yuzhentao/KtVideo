package com.yzt.common.base

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class App : MultiDexApplication() {

    companion object {
        var app: App? = null
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(app)
    }

}