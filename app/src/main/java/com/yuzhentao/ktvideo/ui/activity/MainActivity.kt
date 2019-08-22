package com.yuzhentao.ktvideo.ui.activity

import android.Manifest
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.extension.bindView
import com.yuzhentao.ktvideo.extension.newIntent
import com.yuzhentao.ktvideo.extension.shortToast
import com.yuzhentao.ktvideo.ui.fragment.*
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 主类
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: MainActivity = this

    //    private var tvTop: AppCompatTextView? = null
//    private var ivTop: AppCompatImageView? = null
//    private var rbHome: RadioButton? = null
//    private var rbDiscover: RadioButton? = null
//    private var rbRanking: RadioButton? = null
//    private var rbMine: RadioButton? = null
    private val tvTop by bindView<TextView>(R.id.tv_top)
    private val ivTop by bindView<AppCompatImageView>(R.id.iv_top)
    private val rbHome by bindView<RadioButton>(R.id.rb_home)
    private val rbDiscover by bindView<RadioButton>(R.id.rb_discover)
    private val rbRanking by bindView<RadioButton>(R.id.rb_ranking)
    private val rbMine by bindView<RadioButton>(R.id.rb_mine)

    private var homeFragment: HomeFragment? = null
    private var discoverFragment: DiscoverFragment? = null
    private var rankingFragment: RankingFragment? = null
    private var mineFragment: MineFragment? = null
    private lateinit var searchFragment: SearchFragment

    private var toast: Toast? = null
    private var exitTime: Long = 0

    private var permissionsDisposable: Disposable? = null
//    private var rxPermissions: RxPermissions? = null

    private val rxPermissions: RxPermissions by lazy {
        RxPermissions(activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            navigationBarColor(R.color.black)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_main)
        requestPermissions()
        initView()
        initFragment(savedInstanceState)
    }

    /**
     * let扩展函数的实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作
     * object.let{
     *  it.XXX//在函数体内使用it替代object对象去访问其公有的属性和方法
     * }
     * object?.let{//表示object不为null的条件下，才会去执行let函数体
     *  it.XXX
     * }
     */
    override fun onPause() {
        super.onPause()
        toast?.let {
            //这里表示toast不为null时才会去执行函数体
            toast!!.cancel()
        }
    }

    override fun onDestroy() {
        if (permissionsDisposable != null && !permissionsDisposable!!.isDisposed) {
            permissionsDisposable!!.dispose()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis().minus(exitTime) <= 3000) {
            finish()
            toast!!.cancel()
        } else {
            exitTime = System.currentTimeMillis()
            toast = shortToast("再按一次退出程序")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tb -> {
                when (rg_bottom.checkedRadioButtonId) {
                    R.id.rb_home -> {
                        homeFragment?.scrollToTop()
                    }
                    R.id.rb_discover -> {
                        discoverFragment?.scrollToTop()
                    }
                }
            }
            R.id.iv_top -> {
                if (rbMine.isChecked) {//设置
                    newIntent<SettingActivity>(false)
                } else {//搜索
                    searchFragment = SearchFragment()
                    searchFragment.show(supportFragmentManager, SEARCH_TAG)
                }
            }
            R.id.rb_home -> {
                tvTop.text = getToday()
                tvTop.visibility = View.VISIBLE
                ivTop.setImageResource(R.drawable.ic_search_black)
                rbHome.isSelected = true
                rbDiscover.isSelected = false
                rbRanking.isSelected = false
                rbMine.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .show(homeFragment!!)
                        .hide(discoverFragment!!)
                        .hide(rankingFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_discover -> {
                tvTop.setText(R.string.discover)
                tvTop.visibility = View.VISIBLE
                ivTop.setImageResource(R.drawable.ic_search_black)
                rbHome.isSelected = false
                rbDiscover.isSelected = true
                rbRanking.isSelected = false
                rbMine.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .show(discoverFragment!!)
                        .hide(rankingFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_ranking -> {
                tvTop.setText(R.string.ranking)
                tvTop.visibility = View.VISIBLE
                ivTop.setImageResource(R.drawable.ic_search_black)
                rbHome.isSelected = false
                rbDiscover.isSelected = false
                rbRanking.isSelected = true
                rbMine.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .hide(discoverFragment!!)
                        .show(rankingFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_mine -> {
                tvTop.visibility = View.GONE
                ivTop.setImageResource(R.drawable.ic_settings_black)
                rbHome.isSelected = false
                rbDiscover.isSelected = false
                rbRanking.isSelected = false
                rbMine.isSelected = true
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .hide(discoverFragment!!)
                        .hide(rankingFragment!!)
                        .show(mineFragment!!)
                        .commit()
            }
        }
    }

    private fun requestPermissions() {
//        rxPermissions = RxPermissions(activity)
        permissionsDisposable = rxPermissions
                .requestEachCombined(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { permission ->
                    when {
                        permission.granted -> {

                        }
                        permission.shouldShowRequestPermissionRationale -> {

                        }
                        else -> {

                        }
                    }
                }
    }

    private fun initView() {
        (findViewById<Toolbar>(R.id.tb)).setOnClickListener(this)
//        tvTop = findViewById(R.id.tv_top)
//        ivTop = findViewById(R.id.iv_top)
//        rbHome = findViewById(R.id.rb_home)
//        rbDiscover = findViewById(R.id.rb_discover)
//        rbRanking = findViewById(R.id.rb_ranking)
//        rbMine = findViewById(R.id.rb_mine)
        tvTop.text = getToday()
        tvTop.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tvTop.visibility = View.VISIBLE
        ivTop.setImageResource(R.drawable.ic_search_black)
        ivTop.setOnClickListener(this)
        rbHome.isSelected = true
        rbHome.setOnClickListener(this)
        rbDiscover.setOnClickListener(this)
        rbRanking.setOnClickListener(this)
        rbMine.setOnClickListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fragments: List<Fragment> = supportFragmentManager.fragments
            for (fragment in fragments) {
                if (fragment is HomeFragment) {
                    homeFragment = fragment
                }
                if (fragment is DiscoverFragment) {
                    discoverFragment = fragment
                }
                if (fragment is RankingFragment) {
                    rankingFragment = fragment
                }
                if (fragment is MineFragment) {
                    mineFragment = fragment
                }
            }
        } else {
            homeFragment = HomeFragment()
            discoverFragment = DiscoverFragment()
            rankingFragment = RankingFragment()
            mineFragment = MineFragment()
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.content, homeFragment!!)
                    .add(R.id.content, discoverFragment!!)
                    .add(R.id.content, rankingFragment!!)
                    .add(R.id.content, mineFragment!!)
                    .commit()
        }
        supportFragmentManager
                .beginTransaction()
                .show(homeFragment!!)
                .hide(discoverFragment!!)
                .hide(rankingFragment!!)
                .hide(mineFragment!!)
                .commit()
    }

    private fun getToday(): String {
        val week = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val date = Date()
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return week[index]
    }

}