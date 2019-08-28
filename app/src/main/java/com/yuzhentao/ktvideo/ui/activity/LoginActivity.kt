package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.extension.color
import com.yuzhentao.ktvideo.extension.dimensionPixelOffset
import com.yuzhentao.ktvideo.extension.drawable
import com.yuzhentao.ktvideo.util.DimenUtil
import kotlinx.android.synthetic.main.activity_cache.iv_top
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 */
class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: LoginActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_login)
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
        iv_top.setOnClickListener(this)
        val drawableUser = context.drawable(R.drawable.ic_login_user)
        drawableUser!!.setBounds(
            0,
            0,
            DimenUtil.px2dp(context, context.dimensionPixelOffset(R.dimen.x40).toFloat()),
            DimenUtil.px2dp(context, context.dimensionPixelOffset(R.dimen.x40).toFloat())
        )
        et_user.setCompoundDrawables(drawableUser, null, null, null)
        et_user.compoundDrawablePadding = context.dimensionPixelOffset(R.dimen.x5)
        val drawablePassword = context.drawable(R.drawable.ic_login_password)
        drawablePassword!!.setBounds(
            0,
            0,
            DimenUtil.px2dp(context, context.dimensionPixelOffset(R.dimen.x40).toFloat()),
            DimenUtil.px2dp(context, context.dimensionPixelOffset(R.dimen.x40).toFloat())
        )
        et_password.setCompoundDrawables(drawablePassword, null, null, null)
        et_password.compoundDrawablePadding = context.dimensionPixelOffset(R.dimen.x5)
        val flags = Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        val spannableString = SpannableString(getString(R.string.login_protocol))
        val colorSpan = ForegroundColorSpan(color(R.color.white))
        spannableString.setSpan(colorSpan, 11, getString(R.string.login_protocol).length, flags)
        val styleSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(styleSpan, 11, getString(R.string.login_protocol).length, flags)
        tv_bottom.text = spannableString
    }

    private fun initData() {

    }

}