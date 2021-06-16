package com.yzt.mine.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseActivity
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.ViewUtil
import com.yzt.mine.R
import com.yzt.mine.databinding.ActivityFeedbackBinding

/**
 * 意见反馈
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_FEEDBACK)
class FeedbackActivity : BaseActivity(), View.OnClickListener {

    private var binding: ActivityFeedbackBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtil.setMargins(binding!!.tb, 0, ImmersionBar.getStatusBarHeight(this), 0, 0)
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
        binding!!.tvTop.text = getString(R.string.mine_feedback)
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