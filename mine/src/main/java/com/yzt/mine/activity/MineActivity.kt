package com.yzt.mine.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseActivity
import com.yzt.mine.databinding.ActivityMineBinding

/**
 * @author yzt 2020/12/31
 */
class MineActivity : BaseActivity() {

    private var binding: ActivityMineBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityMineBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}