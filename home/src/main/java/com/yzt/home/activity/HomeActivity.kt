package com.yzt.home.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.home.databinding.ActivityHomeBinding

/**
 * 首页
 *
 * @author yzt 2020/12/31
 */
class HomeActivity : BaseAppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initLifecycleObserver() {

    }

}