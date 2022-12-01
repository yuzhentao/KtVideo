package com.yzt.ktvideo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 视频详情-相关推荐
 *
 * @author yzt 2022/12/1
 */
class VideoRelatedViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoRelatedViewModel() as T
    }

}