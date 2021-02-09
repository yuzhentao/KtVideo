package com.yzt.ktvideo.ui.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseActivity
import com.yzt.ktvideo.databinding.ActivityAvatarBinding

/**
 * 头像
 *
 * @author yzt 2021/2/9
 */
class AvatarActivity : BaseActivity() {

    private var binding: ActivityAvatarBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}