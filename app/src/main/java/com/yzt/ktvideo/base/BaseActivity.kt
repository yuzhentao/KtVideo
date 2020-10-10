package com.yzt.ktvideo.base

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yzt.transitionhelper.TransitionsHelper

abstract class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        TransitionsHelper.unbind(this)
        super.onDestroy()
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

}