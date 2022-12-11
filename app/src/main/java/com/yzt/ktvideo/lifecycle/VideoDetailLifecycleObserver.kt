package com.yzt.ktvideo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.yzt.ktvideo.ui.activity.VideoDetailActivity

/**
 * @author yzt 2022/12/11
 */
class VideoDetailLifecycleObserver(
    lifecycleOwner: LifecycleOwner,
    private val videoManager: VideoDetailActivity.VideoManager
) : DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        videoManager.prepareVideo()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        videoManager.setNoPause()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        videoManager.setPause()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        videoManager.releaseVideo()
        super.onDestroy(owner)
        owner.lifecycle.removeObserver(this)
    }

}