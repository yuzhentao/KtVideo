package com.yzt.discover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 发现-详情-广场
 *
 * @author yzt 2022/12/1
 */
class DiscoverDetailRightViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscoverDetailRightViewModel() as T
    }

}