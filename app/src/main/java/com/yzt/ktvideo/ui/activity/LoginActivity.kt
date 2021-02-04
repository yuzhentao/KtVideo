package com.yzt.ktvideo.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseActivity
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.extension.drawable
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.DimenUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private var binding: ActivityLoginBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            navigationBarColor(R.color.black)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.ivTop!!.setOnClickListener(this)
        val drawableUser = drawable(R.drawable.ic_login_user)
        drawableUser!!.setBounds(
            0,
            0,
            DimenUtil.px2dp(context!!, dimensionPixelOffset(R.dimen.x40).toFloat()),
            DimenUtil.px2dp(context!!, dimensionPixelOffset(R.dimen.x40).toFloat())
        )
        binding?.etUser!!.setCompoundDrawables(drawableUser, null, null, null)
        binding?.etUser!!.compoundDrawablePadding = dimensionPixelOffset(R.dimen.x5)
        val drawablePassword = drawable(R.drawable.ic_login_password)
        drawablePassword!!.setBounds(
            0,
            0,
            DimenUtil.px2dp(context!!, dimensionPixelOffset(R.dimen.x40).toFloat()),
            DimenUtil.px2dp(context!!, dimensionPixelOffset(R.dimen.x40).toFloat())
        )
        binding?.etPassword!!.setCompoundDrawables(drawablePassword, null, null, null)
        binding?.etPassword!!.compoundDrawablePadding = dimensionPixelOffset(R.dimen.x5)
        val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        val spannableString = SpannableString(getString(R.string.login_protocol))
        val colorSpan = ForegroundColorSpan(color(R.color.white))
        spannableString.setSpan(colorSpan, 11, getString(R.string.login_protocol).length, flags)
        val styleSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan, 11, getString(R.string.login_protocol).length, flags)
        tv_bottom.text = spannableString
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