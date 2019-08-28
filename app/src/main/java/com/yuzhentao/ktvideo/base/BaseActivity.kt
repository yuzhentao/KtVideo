package com.yuzhentao.ktvideo.base

import androidx.appcompat.app.AppCompatActivity
import com.yuzhentao.transitionhelper.TransitionsHelper

abstract class BaseActivity : AppCompatActivity() {

    override fun onDestroy() {
        TransitionsHelper.unbind(this)
        super.onDestroy()
    }

}