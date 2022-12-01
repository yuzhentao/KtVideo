package com.yzt.ktvideo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 热门搜索词
 *
 * @author yzt 2022/12/1
 */
class HotSearchViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HotSearchViewModel() as T
    }

}