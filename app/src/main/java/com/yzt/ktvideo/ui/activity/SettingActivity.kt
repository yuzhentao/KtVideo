package com.yzt.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.ktvideo.R
import com.yzt.ktvideo.util.ClickUtil
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 设置
 */
class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: SettingActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_setting)
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
        tv_top.text = getString(R.string.mine_setting)
        iv_top.setOnClickListener(this)
    }

    private fun initData() {

    }

}