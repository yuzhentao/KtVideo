package com.yzt.home

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseActivity
import com.yzt.home.databinding.ActivityHomeBinding

/**
 * @author yzt 2020/12/31
 */
class HomeActivity : BaseActivity() {

    private var binding: ActivityHomeBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}