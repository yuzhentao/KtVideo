package com.yuzhentao.ktvideo.ui.activity

import android.Manifest
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.ui.fragment.*
import com.yuzhentao.ktvideo.extension.newIntent
import com.yuzhentao.ktvideo.extension.shortToast
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 主类
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    var context: Context = this
    var activity: MainActivity = this

    private var tvTitle: AppCompatTextView? = null
    private var ivSearch: AppCompatImageView? = null
    private var rbHome: RadioButton? = null
    private var rbDiscover: RadioButton? = null
    private var rbRanking: RadioButton? = null
    private var rbMine: RadioButton? = null

    private var homeFragment: HomeFragment? = null
    private var discoverFragment: DiscoverFragment? = null
    private var hotFragment: HotFragment? = null
    private var mineFragment: MineFragment? = null
    private lateinit var searchFragment: SearchFragment

    private var toast: Toast? = null
    private var exitTime: Long = 0

    private var permissionsDisposable: Disposable? = null
    private var rxPermissions: RxPermissions? = null

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
                homeFragment?.scrollToTop()
            }
            R.id.iv_top -> {
                if (rbMine?.isChecked!!) {//设置
                    newIntent<SettingActivity>(false)
                } else {//搜索
                    searchFragment = SearchFragment()
                    searchFragment.show(supportFragmentManager, SEARCH_TAG)
                }
            }
            R.id.rb_home -> {
                tvTitle?.text = getToday()
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.ic_search_black)
                rbHome?.isSelected = true
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = false
                rbMine?.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .show(homeFragment!!)
                        .hide(discoverFragment!!)
                        .hide(hotFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_discover -> {
                tvTitle?.setText(R.string.discover)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.ic_search_black)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = true
                rbRanking?.isSelected = false
                rbMine?.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .show(discoverFragment!!)
                        .hide(hotFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_ranking -> {
                tvTitle?.setText(R.string.ranking)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.ic_search_black)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = true
                rbMine?.isSelected = false
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .hide(discoverFragment!!)
                        .show(hotFragment!!)
                        .hide(mineFragment!!)
                        .commit()
            }
            R.id.rb_mine -> {
                tvTitle?.visibility = View.GONE
                ivSearch?.setImageResource(R.drawable.ic_settings_black)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = false
                rbMine?.isSelected = true
                supportFragmentManager
                        .beginTransaction()
                        .hide(homeFragment!!)
                        .hide(discoverFragment!!)
                        .hide(hotFragment!!)
                        .show(mineFragment!!)
                        .commit()
            }
        }
    }

    private fun requestPermissions() {
        rxPermissions = RxPermissions(this)
        permissionsDisposable = rxPermissions!!
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
        tvTitle = findViewById(R.id.tv_top)
        ivSearch = findViewById(R.id.iv_top)
        rbHome = findViewById(R.id.rb_home)
        rbDiscover = findViewById(R.id.rb_discover)
        rbRanking = findViewById(R.id.rb_ranking)
        rbMine = findViewById(R.id.rb_mine)
        tvTitle?.text = getToday()
        tvTitle?.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tvTitle?.visibility = View.VISIBLE
        ivSearch?.setImageResource(R.drawable.ic_search_black)
        ivSearch?.setOnClickListener(this)
        rbHome?.isSelected = true
        rbHome?.setOnClickListener(this)
        rbDiscover?.setOnClickListener(this)
        rbRanking?.setOnClickListener(this)
        rbMine?.setOnClickListener(this)
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
                if (fragment is HotFragment) {
                    hotFragment = fragment
                }
                if (fragment is MineFragment) {
                    mineFragment = fragment
                }
            }
        } else {
            homeFragment = HomeFragment()
            discoverFragment = DiscoverFragment()
            hotFragment = HotFragment()
            mineFragment = MineFragment()
            val ft = supportFragmentManager.beginTransaction()
            ft.add(R.id.content, homeFragment!!)
                    .add(R.id.content, discoverFragment!!)
                    .add(R.id.content, hotFragment!!)
                    .add(R.id.content, mineFragment!!)
                    .commit()
        }
        supportFragmentManager
                .beginTransaction()
                .show(homeFragment!!)
                .hide(discoverFragment!!)
                .hide(hotFragment!!)
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