package com.yuzhentao.ktvideo.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.arialyy.aria.core.Aria
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.util.newIntent
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //隐藏导航栏
        val window = window
        val params = window.attributes
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.attributes = params
        setContentView(R.layout.activity_splash)
        initView()
        anim()
        Aria.get(this).downloadConfig.threadNum = 5
        Aria.get(this).downloadConfig.maxTaskNum = 5
    }

    private fun initView() {
        val font: Typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tv_name_cn.typeface = font
        tv_name_en.typeface = font
    }

    private fun anim() {
        val alphaAnim = AlphaAnimation(0.1F, 1.0F)
        alphaAnim.duration = 1000
        val scaleAnim = ScaleAnimation(0.1F, 1.0F, 0.1F, 1.0F, ScaleAnimation.RELATIVE_TO_SELF, 0.5F, ScaleAnimation.RELATIVE_TO_SELF, 0.5F)
        scaleAnim.duration = 1000
        val animSet = AnimationSet(true)
        animSet.addAnimation(alphaAnim)
        animSet.addAnimation(scaleAnim)
        animSet.duration = 1000
        animSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                newIntent<MainActivity>(true)
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        iv.startAnimation(animSet)
    }

}