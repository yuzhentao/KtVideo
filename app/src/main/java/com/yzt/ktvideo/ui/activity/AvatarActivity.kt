package com.yzt.ktvideo.ui.activity

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseActivity
import com.yzt.common.util.ClickUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.databinding.ActivityAvatarBinding

/**
 * 头像
 *
 * @author yzt 2021/2/9
 */
class AvatarActivity : BaseActivity(), View.OnClickListener {

    private var binding: ActivityAvatarBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityAvatarBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
        binding!!.tvTop.text = getString(R.string.mine_avatar)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressed()
                }
            }
        }
    }

}