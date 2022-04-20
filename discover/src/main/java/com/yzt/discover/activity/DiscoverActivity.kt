package com.yzt.discover.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.discover.databinding.ActivityDiscoverBinding

/**
 * 发现
 *
 * @author yzt 2020/12/31
 */
class DiscoverActivity : BaseAppCompatActivity() {

    private var binding: ActivityDiscoverBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}