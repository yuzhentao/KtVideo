package com.yzt.mine.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.common.key.Constants
import com.yzt.common.util.ClickUtil
import com.yzt.mine.R
import com.yzt.mine.databinding.ActivityFavoriteBinding

/**
 * 收藏
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constants.PATH_FAVORITE)
class FavoriteActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var binding: ActivityFavoriteBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.tvTop.text = getString(R.string.mine_favorite)
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initLifecycleObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

}