package com.yzt.ktvideo.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseActivity
import com.yzt.common.extension.color
import com.yzt.common.extension.dimensionPixelOffset
import com.yzt.common.extension.drawable
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.DimenUtil
import com.yzt.ktvideo.R
import com.yzt.transitionhelper.TransitionsHelper
import com.yzt.transitionhelper.bean.InfoBean
import com.yzt.transitionhelper.method.ColorShowMethod
import kotlinx.android.synthetic.main.activity_login.*

/**
 * 登录
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: LoginActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            navigationBarColor(R.color.black)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_login)
        TransitionsHelper.build(this)
            .setShowMethod(object : ColorShowMethod(R.color.purple, R.color.pink) {
                override fun loadPlaceholder(bean: InfoBean<*>, placeholder: ImageView) {
                    val set = AnimatorSet()
                    set.playTogether(
                        ObjectAnimator.ofFloat(placeholder, "rotation", 0F, 180F),
                        ObjectAnimator.ofFloat(placeholder, "scaleX", 1F, 0F),
                        ObjectAnimator.ofFloat(placeholder, "scaleY", 1F, 0F)
                    )
                    set.interpolator = AccelerateInterpolator()
                    set.setDuration((showDuration / 4 * 5).toLong()).start()
                }

                override fun loadTargetView(bean: InfoBean<*>, targetView: View) {

                }
            })
            .setExposeColor(context.color(R.color.pink))
            .show()
        initView()
        initData()
    }

    override fun onDestroy() {
        TransitionsHelper.unbind(this)
        super.onDestroy()
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