package com.yuzhentao.ktvideo.ui.activity

import android.Manifest
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.base.BaseActivity
import com.yuzhentao.ktvideo.extension.bindView
import com.yuzhentao.ktvideo.extension.color
import com.yuzhentao.ktvideo.extension.newIntent
import com.yuzhentao.ktvideo.extension.shortToast
import com.yuzhentao.ktvideo.interfaces.OnRvScrollListener
import com.yuzhentao.ktvideo.ui.fragment.*
import com.yuzhentao.ktvideo.util.ClickUtil
import com.yuzhentao.ktvideo.util.DimenUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 主类
 */
class MainActivity : BaseActivity(), View.OnClickListener {

    private var context: Context = this
    private var activity: MainActivity = this

    private val tb by bindView<Toolbar>(R.id.tb)
    private val tvTitle by bindView<AppCompatTextView>(R.id.tv_title)
    private val ivSearch by bindView<AppCompatImageView>(R.id.iv_search)
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
    private var changeBgHome: Boolean = false
    private var changeBgDiscover: Boolean = false

    private val rxPermissions: RxPermissions by lazy {
        RxPermissions(activity)
    }
    private var permissionsDisposable: Disposable? = null

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
        permissionsDisposable?.let {
            if (!permissionsDisposable!!.isDisposed) {
                permissionsDisposable!!.dispose()
            }
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tb -> {
                if (!ClickUtil.isFastDoubleClick(R.id.tb, 1000)) {
                    when (rg_bottom.checkedRadioButtonId) {
                        R.id.rb_home -> {
                            homeFragment?.scrollToTop()
                        }
                        R.id.rb_discover -> {
                            discoverFragment?.scrollToTop()
                        }
                    }
                }
            }
            R.id.iv_search -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_search, 1000)) {
                    if (rbMine.isChecked) {//设置
                        newIntent<SettingActivity>(false)
                    } else {//搜索
                        searchFragment = SearchFragment()
                        searchFragment.show(supportFragmentManager, SEARCH_TAG)
                    }
                }
            }
            R.id.rb_home -> {
                if (!ClickUtil.isFastDoubleClick(R.id.rb_home, 1000)) {
                    tb.setBackgroundColor(
                        if (changeBgHome) {
                            context.color(R.color.white_50)
                        } else context.color(R.color.white)
                    )
                    tb.elevation = DimenUtil.dp2px(context, 4).toFloat()
                    tvTitle.text = getToday()
                    tvTitle.visibility = View.VISIBLE
                    ivSearch.setImageResource(R.drawable.ic_search_black)
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
            }
            R.id.rb_discover -> {
                if (!ClickUtil.isFastDoubleClick(R.id.rb_discover, 1000)) {
                    tb.setBackgroundColor(
                        if (changeBgDiscover) {
                            context.color(R.color.white_50)
                        } else context.color(R.color.white)
                    )
                    tb.elevation = DimenUtil.dp2px(context, 4).toFloat()
                    tvTitle.setText(R.string.discover)
                    tvTitle.visibility = View.VISIBLE
                    ivSearch.setImageResource(R.drawable.ic_search_black)
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
            }
            R.id.rb_ranking -> {
                if (!ClickUtil.isFastDoubleClick(R.id.rb_ranking, 1000)) {
                    tb.setBackgroundColor(context.color(R.color.white))
                    tb.elevation = 0F
                    tvTitle.setText(R.string.ranking)
                    tvTitle.visibility = View.VISIBLE
                    ivSearch.setImageResource(R.drawable.ic_search_black)
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
            }
            R.id.rb_mine -> {
                if (!ClickUtil.isFastDoubleClick(R.id.rb_mine, 1000)) {
                    tb.setBackgroundColor(context.color(R.color.white))
                    tb.elevation = DimenUtil.dp2px(context, 4).toFloat()
                    tvTitle.visibility = View.GONE
                    ivSearch.setImageResource(R.drawable.ic_settings_black)
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
            R.id.fab -> {

            }
        }
    }

    private fun requestPermissions() {
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
        tb.setOnClickListener(this)
        tvTitle.text = getToday()
        tvTitle.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        tvTitle.visibility = View.VISIBLE
        ivSearch.setImageResource(R.drawable.ic_search_black)
        ivSearch.setOnClickListener(this)
        rbHome.isSelected = true
        rbHome.setOnClickListener(this)
        rbDiscover.setOnClickListener(this)
        rbRanking.setOnClickListener(this)
        rbMine.setOnClickListener(this)
        fab.setOnClickListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fragments: List<androidx.fragment.app.Fragment> = supportFragmentManager.fragments
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
        homeFragment?.setOnRvScrollListener(object : OnRvScrollListener {
            override fun onRvScroll(totalDy: Int) {
                tb.setBackgroundColor(
                    if (totalDy < 0) {
                        this@MainActivity.changeBgHome = true
                        context.color(R.color.white_50)
                    } else {
                        this@MainActivity.changeBgHome = false
                        context.color(R.color.white)
                    }
                )
            }
        })
        discoverFragment?.setOnRvScrollListener(object : OnRvScrollListener {
            override fun onRvScroll(totalDy: Int) {
                tb.setBackgroundColor(
                    if (totalDy < 0) {
                        this@MainActivity.changeBgDiscover = true
                        context.color(R.color.white_50)
                    } else {
                        this@MainActivity.changeBgDiscover = false
                        context.color(R.color.white)
                    }
                )
            }
        })
    }

    private fun getToday(): String {
        val week =
            arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
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