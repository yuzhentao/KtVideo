package com.yzt.ktvideo.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.arialyy.aria.core.Aria
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.bean.SplashBean
import com.yzt.common.base.BaseActivity
import com.yzt.common.extension.ioMain
import com.yzt.common.extension.newIntent
import com.yzt.common.key.Constant.KT_VIDEO
import com.yzt.common.util.ImageUtil
import com.yzt.common.util.SPUtils
import com.yzt.ktvideo.R
import com.yzt.ktvideo.databinding.ActivitySplashBinding
import com.yzt.ktvideo.mvp.contract.SplashContract
import com.yzt.ktvideo.mvp.presenter.SplashPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 闪屏
 *
 * @author yzt 2021/2/9
 */
const val SPLASH_URL = "splash_url"

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), SplashContract.View {

    private var binding: ActivitySplashBinding? = null

    private val presenter: SplashPresenter by lazy {
        SplashPresenter(context!!, this)
    }

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {
        installSplashScreen()
    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
        }
        Aria.get(this).downloadConfig.threadNum = 5
        Aria.get(this).downloadConfig.maxTaskNum = 5
    }

    override fun initView(savedInstanceState: Bundle?) {
        presenter.load()
        val font: Typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        binding!!.tvIntroEn.typeface = font
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun setData(bean: SplashBean?) {
        anim(bean)
    }

    private fun anim(bean: SplashBean?) {
        require(bean != null) {
            newIntent<MainActivity>(true)
            return
        }

        Observable
            .create(ObservableOnSubscribe<String> { emitter ->
                val url = SPUtils.getInstance(context!!, KT_VIDEO).getString(SPLASH_URL)
                emitter.onNext(url)
            })
            .ioMain()
            .subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(url: String) {
                    if (bean.startPage != null && !bean.startPage!!.imageUrl.isNullOrEmpty()) {
                        ImageUtil.show(context!!, binding!!.ivBg, bean.startPage!!.imageUrl!!)
                        if (bean.startPage!!.imageUrl != url) {
                            SPUtils.getInstance(context!!, KT_VIDEO)
                                .put(SPLASH_URL, bean.startPage!!.imageUrl!!)
                        }
                    } else {
                        if (url.isNotEmpty()) {
                            ImageUtil.show(context!!, binding!!.ivBg, url)
                        } else {
                            binding!!.ivBg.setImageResource(R.drawable.bg_splash)
                        }
                    }
                }

                override fun onError(e: Throwable) {

                }
            })
        val alphaAnimBg = ObjectAnimator.ofFloat(binding!!.ivBg, "alpha", 0.1F, 1.0F)
        val scaleAnimXBg = ObjectAnimator.ofFloat(binding!!.ivBg, "scaleX", 1.0F, 1.2F)
        val scaleAnimYBg = ObjectAnimator.ofFloat(binding!!.ivBg, "scaleY", 1.0F, 1.2F)
        val animSetBg = AnimatorSet()
        animSetBg.play(alphaAnimBg).with(scaleAnimXBg).with(scaleAnimYBg)
        animSetBg.duration = 2000
        animSetBg.interpolator = FastOutSlowInInterpolator()
        animSetBg.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                newIntent<MainActivity>(true)
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationStart(animation: Animator) {

            }
        })
        animSetBg.start()
        val alphaAnimLogo = ObjectAnimator.ofFloat(binding!!.ivLogo, "alpha", 0.1F, 1.0F)
        alphaAnimLogo.duration = 2000
        alphaAnimLogo.interpolator = FastOutSlowInInterpolator()
        alphaAnimLogo.start()
    }

}