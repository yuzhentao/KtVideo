package com.yzt.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 首页
 *
 * @author yzt 2022/12/1
 */
class HomeViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }

}