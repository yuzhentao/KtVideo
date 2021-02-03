package com.yzt.common.base

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

class App : MultiDexApplication() {

    companion object {

        @JvmStatic
        var app: App? = null

    }

    override fun onCreate() {
        super.onCreate()
        app = this
        initARouter()
        initTimber()
        initRealm()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(app)
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.init(app)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRealm() {
        Realm.init(this)
        val config: RealmConfiguration? = RealmConfiguration.Builder()
            .name("ktvideo.realm")//文件名
            .schemaVersion(0)//版本号
            .build()
        config?.let {
            Realm.setDefaultConfiguration(it)
        }
    }

}