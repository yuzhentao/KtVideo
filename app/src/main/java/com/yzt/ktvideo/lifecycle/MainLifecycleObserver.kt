package com.yzt.ktvideo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yzt.ktvideo.ui.activity.MainActivity

/**
 * @author yzt 2022/12/10
 */
class MainLifecycleObserver(
    lifecycleOwner: LifecycleOwner,
    private val manager: MainActivity.LifecycleManager
) : DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        manager.requestPermissions()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        manager.cancelToast()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        manager.dispose()
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }

}