package com.yzt.ktvideo.base

import androidx.appcompat.app.AppCompatActivity
import com.yzt.transitionhelper.TransitionsHelper

abstract class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        TransitionsHelper.unbind(this)
        super.onDestroy()
    }

}