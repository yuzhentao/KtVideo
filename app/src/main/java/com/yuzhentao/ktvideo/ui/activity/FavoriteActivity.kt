package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 收藏
 */
class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: FavoriteActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_favorite)
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
        tv_top.text = getString(R.string.mine_favorite)
        iv_top.setOnClickListener(this)
    }

    private fun initData() {

    }

}