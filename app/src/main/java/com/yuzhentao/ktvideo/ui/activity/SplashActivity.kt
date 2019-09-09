package com.yuzhentao.ktvideo.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.arialyy.aria.core.Aria
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.bean.SplashBean
import com.yuzhentao.ktvideo.extension.ioMain
import com.yuzhentao.ktvideo.extension.newIntent
import com.yuzhentao.ktvideo.mvp.contract.SplashContract
import com.yuzhentao.ktvideo.mvp.presenter.SplashPresenter
import com.yuzhentao.ktvideo.util.ImageUtil
import com.yuzhentao.ktvideo.util.SPUtils
import com.yuzhentao.ktvideo.worker.DownloadSplashWorker
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 闪屏页
 */
class SplashActivity : AppCompatActivity(), SplashContract.View {

    private var context: Context = this

    private val presenter: SplashPresenter by lazy {
        SplashPresenter(context, this)
    }

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
        presenter.load()
        val font: Typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tv_intro_en.typeface = font
    }

    private fun anim(bean: SplashBean?) {
        Observable
            .create(ObservableOnSubscribe<String> { emitter ->
                val url = SPUtils.getInstance(context, "KtVideo").getString("splash_url")
                emitter.onNext(url)
            })
            .ioMain()
            .subscribe(object : Observer<String> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(url: String) {
                    if (bean?.startPage != null && !bean.startPage.imageUrl.isNullOrEmpty()) {
                        ImageUtil.show(context, iv_bg, bean.startPage.imageUrl)
                        if (bean.startPage.imageUrl != url) {
                            downloadSplash(bean.startPage.imageUrl)
                        }
                    } else {
                        if (url.isNotEmpty()) {
                            ImageUtil.show(context, iv_bg, url)
                        } else {
                            iv_bg.setImageResource(R.drawable.bg_splash)
                        }
                    }
                }

                override fun onError(e: Throwable) {

                }
            })
        val alphaAnimBg = ObjectAnimator.ofFloat(iv_bg, "alpha", 0.1F, 1.0F)
        val scaleAnimXBg = ObjectAnimator.ofFloat(iv_bg, "scaleX", 1.0F, 1.2F)
        val scaleAnimYBg = ObjectAnimator.ofFloat(iv_bg, "scaleY", 1.0F, 1.2F)
        val animSetBg = AnimatorSet()
        animSetBg.play(alphaAnimBg).with(scaleAnimXBg).with(scaleAnimYBg)
        animSetBg.duration = 2000
        animSetBg.interpolator = FastOutSlowInInterpolator()
        animSetBg.addListener(object : Animator.AnimatorListener {
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
        animSetBg.start()
        val alphaAnimLogo = ObjectAnimator.ofFloat(iv_logo, "alpha", 0.1F, 1.0F)
        alphaAnimLogo.duration = 2000
        alphaAnimLogo.interpolator = FastOutSlowInInterpolator()
        alphaAnimLogo.start()
    }

    private fun downloadSplash(url: String) {
        SPUtils.getInstance(context, "KtVideo").put("splash_url", url)
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(false)//内存不足时不执行
            .setRequiresBatteryNotLow(false)//电量低时不执行
            .build()
        val request = OneTimeWorkRequest.Builder(DownloadSplashWorker::class.java)
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(context)
            .enqueue(request)
    }

}