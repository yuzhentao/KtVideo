package com.yzt.mine.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.mine.databinding.ActivityMineBinding

/**
 * 我的
 *
 * @author yzt 2020/12/31
 */
class MineActivity : BaseAppCompatActivity() {

    private var binding: ActivityMineBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}