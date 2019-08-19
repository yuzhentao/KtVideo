package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 评论
 */
class CommentActivity : AppCompatActivity(), View.OnClickListener {

    var context: Context = this
    var activity: CommentActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_comment)
        initView()
        initData()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                onBackPressed()
            }
        }
    }

    private fun initView() {
        tv_top.text = getString(R.string.mine_comment)
        iv_top.setOnClickListener(this)
    }

    private fun initData() {

    }

}