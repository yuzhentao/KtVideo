package com.yuzhentao.ktvideo.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v7.app.AppCompatActivity
import com.arialyy.aria.core.Aria
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.SplashBean
import com.yuzhentao.ktvideo.extension.newIntent
import com.yuzhentao.ktvideo.mvp.contract.SplashContract
import com.yuzhentao.ktvideo.mvp.presenter.SplashPresenter
import com.yuzhentao.ktvideo.util.ImageUtil
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 闪屏页
 */
class SplashActivity : AppCompatActivity(), SplashContract.View {

    private var context: Context = this

    private var presenter: SplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
        }
        setContentView(R.layout.activity_splash)
        initView()
        Aria.get(this).downloadConfig.threadNum = 5
        Aria.get(this).downloadConfig.maxTaskNum = 5
    }

    override fun setData(bean: SplashBean?) {
        anim(bean)
    }

    private fun initView() {
        presenter = SplashPresenter(context, this)
        presenter?.load()
        val font: Typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tv_intro_en.typeface = font
    }

    private fun anim(bean: SplashBean?) {
        if (bean?.startPage != null && !bean.startPage.imageUrl.isNullOrEmpty()) {
            ImageUtil.show(context, iv_bg, bean.startPage.imageUrl)
        } else {
            iv_bg.setImageResource(R.drawable.bg_splash)
        }
        val alphaAnim = ObjectAnimator.ofFloat(iv_bg, "alpha", 0.1F, 1.0F)
        val scaleAnimX = ObjectAnimator.ofFloat(iv_bg, "scaleX", 1.0F, 1.2F)
        val scaleAnimY = ObjectAnimator.ofFloat(iv_bg, "scaleY", 1.0F, 1.2F)
        val animSet = AnimatorSet()
        animSet.play(alphaAnim).with(scaleAnimX).with(scaleAnimY)
        animSet.duration = 2000
        animSet.interpolator = FastOutSlowInInterpolator()
        animSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                newIntent<MainActivity>(true)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        animSet.start()
    }

}