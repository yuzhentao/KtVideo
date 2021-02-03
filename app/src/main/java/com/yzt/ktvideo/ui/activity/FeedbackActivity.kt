package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.ktvideo.R
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 意见反馈
 */
@Route(path = Constant.PATH_FEEDBACK)
class FeedbackActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: FeedbackActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_feedback)
        initView()
        initData()
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

    private fun initView() {
        tv_top.text = getString(R.string.mine_feedback)
        iv_top.setOnClickListener(this)
    }

    private fun initData() {

    }

}