package com.yzt.discover

import android.view.LayoutInflater
import android.view.View
import com.yzt.common.base.BaseFragment
import com.yzt.discover.databinding.FragmentDiscoverBinding

/**
 * @author yzt 2020/12/31
 */
class DiscoverFragment : BaseFragment() {

    private var binding: FragmentDiscoverBinding? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentDiscoverBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

}