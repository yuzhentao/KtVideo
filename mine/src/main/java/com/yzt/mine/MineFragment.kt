package com.yzt.mine

import android.view.LayoutInflater
import android.view.View
import com.yzt.common.base.BaseFragment
import com.yzt.mine.databinding.FragmentMineBinding

/**
 * @author yzt 2020/12/31
 */
class MineFragment : BaseFragment() {

    private var binding: FragmentMineBinding? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentMineBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

}