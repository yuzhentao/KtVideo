package com.yzt.discover.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yzt.discover.activity.DiscoverDetailActivity

/**
 * @author yzt 2022/12/11
 */
class DiscoverDetailLifecycleObserver(
    lifecycleOwner: LifecycleOwner,
    private val manager: DiscoverDetailActivity.LifecycleManager
) : DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        manager.resumeVideo()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        manager.pauseVideo()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        manager.releaseVideo()
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }


}