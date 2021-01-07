package com.yzt.home

import android.view.LayoutInflater
import android.view.View
import com.yzt.common.base.BaseFragment
import com.yzt.home.databinding.FragmentHomeBinding

/**
 * @author yzt 2020/12/31
 */
class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

}