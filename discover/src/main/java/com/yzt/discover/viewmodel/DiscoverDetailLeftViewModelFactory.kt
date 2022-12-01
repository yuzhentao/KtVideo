package com.yzt.discover.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 发现-详情-推荐
 *
 * @author yzt 2022/12/1
 */
class DiscoverDetailLeftViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DiscoverDetailLeftViewModel() as T
    }

}