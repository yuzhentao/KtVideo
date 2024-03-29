package com.yzt.ktvideo.ui.activity

import android.Manifest
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.common.extension.color
import com.yzt.common.extension.newIntent
import com.yzt.common.extension.shortToast
import com.yzt.common.key.Keys
import com.yzt.common.listener.OnRvScrollListener
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.DimenUtil
import com.yzt.common.util.ViewUtil
import com.yzt.discover.fragment.DiscoverFragment
import com.yzt.home.fragment.HomeFragment
import com.yzt.ktvideo.R
import com.yzt.ktvideo.databinding.ActivityMainBinding
import com.yzt.ktvideo.lifecycle.MainLifecycleObserver
import com.yzt.ktvideo.ui.fragment.SearchFragment
import com.yzt.ktvideo.worker.DownloadSplashWorker
import com.yzt.mine.fragment.MineFragment
import com.yzt.ranking.fragment.RankingFragment
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 入口
 *
 * @author yzt 2021/2/9
 */
class MainActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var binding: ActivityMainBinding? = null

    private var homeFragment: HomeFragment? = null
    private var discoverFragment: DiscoverFragment? = null
    private var rankingFragment: RankingFragment? = null
    private var mineFragment: MineFragment? = null
    private lateinit var searchFragment: SearchFragment

    private var toast: Toast? = null
    private var exitTime: Long = 0
    private var changeBgHome: Boolean = false
    private var changeBgDiscover: Boolean = false

    private var permissionsDisposable: Disposable? = null

    private lateinit var lifecycleObserver: MainLifecycleObserver

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis().minus(exitTime) <= 3000) {
                    finish()
                    toast!!.cancel()
                } else {
                    exitTime = System.currentTimeMillis()
                    toast = shortToast("再按一次退出程序")
                }
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewUtil.setPaddings(
            binding!!.tb,
            0,
            ImmersionBar.getStatusBarHeight(this),
            0,
            0,
        )
        binding!!.tb.setOnClickListener(this)
        binding!!.tb.setOnLongClickListener { true }
        binding!!.tvTitle.text = getToday()
        binding!!.tvTitle.typeface = Typeface.createFromAsset(assets, "fonts/Lobster-1.4.otf")
        binding!!.tvTitle.visibility = View.VISIBLE
        binding!!.ivSearch.setImageResource(R.drawable.ic_search_black)
        binding!!.ivSearch.setOnClickListener(this)
        binding!!.rbHome.isSelected = true
        binding!!.rbHome.setOnClickListener(this)
        binding!!.rbHome.setOnLongClickListener { true }
        binding!!.rbDiscover.setOnClickListener(this)
        binding!!.rbDiscover.setOnLongClickListener { true }
        binding!!.rbRanking.setOnClickListener(this)
        binding!!.rbRanking.setOnLongClickListener { true }
        binding!!.rbMine.setOnClickListener(this)
        binding!!.rbMine.setOnLongClickListener { true }
        binding!!.fab.setOnClickListener(this)
        binding!!.fab.setOnLongClickListener { true }
        initFragment(savedInstanceState)
//        context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath
//        + File.separator
//        + Constants.SPLASH_IMAGE_FOLDER_NAME
//        + File.separator
//        + Constants.SPLASH_IMAGE_FILE_NAME
    }

    override fun initData(savedInstanceState: Bundle?) {
        downloadSplash()
    }

    override fun initLifecycleObserver() {
        lifecycleObserver = MainLifecycleObserver(this, LifecycleManager())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tb -> {
                if (!ClickUtil.isFastDoubleClick(R.id.tb, 1000)) {
                    when (binding!!.rgBottom.checkedRadioButtonId) {
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
                    if (binding!!.rbMine.isChecked) {//设置
                        newIntent<SettingActivity>(false)
                    } else {//搜索
                        searchFragment = SearchFragment()
                        searchFragment.show(supportFragmentManager, searchFragment.simpleName)
                    }
                }
            }
            R.id.rb_home -> {
                if (!ClickUtil.isFastDoubleClick(R.id.rb_home, 1000)) {
                    binding!!.tb.setBackgroundColor(
                        if (changeBgHome) {
                            color(R.color.white_50)
                        } else color(R.color.white)
                    )
                    binding!!.tb.elevation = DimenUtil.dp2px(context!!, 4).toFloat()
                    binding!!.tvTitle.text = getToday()
                    binding!!.tvTitle.visibility = View.VISIBLE
                    binding!!.ivSearch.setImageResource(R.drawable.ic_search_black)
                    binding!!.rbHome.isSelected = true
                    binding!!.rbDiscover.isSelected = false
                    binding!!.rbRanking.isSelected = false
                    binding!!.rbMine.isSelected = false
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
                    binding!!.tb.setBackgroundColor(
                        if (changeBgDiscover) {
                            color(R.color.white_50)
                        } else color(R.color.white)
                    )
                    binding!!.tb.elevation = DimenUtil.dp2px(context!!, 4).toFloat()
                    binding!!.tvTitle.setText(R.string.discover)
                    binding!!.tvTitle.visibility = View.VISIBLE
                    binding!!.ivSearch.setImageResource(R.drawable.ic_search_black)
                    binding!!.rbHome.isSelected = false
                    binding!!.rbDiscover.isSelected = true
                    binding!!.rbRanking.isSelected = false
                    binding!!.rbMine.isSelected = false
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
                    binding!!.tb.setBackgroundColor(color(R.color.white))
                    binding!!.tb.elevation = 0F
                    binding!!.tvTitle.setText(R.string.ranking)
                    binding!!.tvTitle.visibility = View.VISIBLE
                    binding!!.ivSearch.setImageResource(R.drawable.ic_search_black)
                    binding!!.rbHome.isSelected = false
                    binding!!.rbDiscover.isSelected = false
                    binding!!.rbRanking.isSelected = true
                    binding!!.rbMine.isSelected = false
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
                    binding!!.tb.setBackgroundColor(color(R.color.white))
                    binding!!.tb.elevation = DimenUtil.dp2px(context!!, 4).toFloat()
                    binding!!.tvTitle.visibility = View.GONE
                    binding!!.ivSearch.setImageResource(R.drawable.ic_settings_black)
                    binding!!.rbHome.isSelected = false
                    binding!!.rbDiscover.isSelected = false
                    binding!!.rbRanking.isSelected = false
                    binding!!.rbMine.isSelected = true
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

    private fun downloadSplash() {
        val mmkv = MMKV.defaultMMKV()
        val url = mmkv.decodeString(Keys.SPLASH_URL, "")
        require(!url.isNullOrEmpty()) {
            return
        }

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(false)//内存不足时不执行
            .setRequiresBatteryNotLow(false)//电量低时不执行
            .build()
        val request = OneTimeWorkRequest.Builder(DownloadSplashWorker::class.java)
            .setInputData(Data.Builder().putString(Keys.SPLASH_URL, url).build())
            .setConstraints(constraints)
            .build()
        WorkManager
            .getInstance(context!!)
            .enqueue(request)
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
        homeFragment?.setOnRvScrollListener(
            OnRvScrollListener {
                binding!!.tb.setBackgroundColor(
                    if (it < 0) {
                        this@MainActivity.changeBgHome = true
                        color(R.color.white_50)
                    } else {
                        this@MainActivity.changeBgHome = false
                        color(R.color.white)
                    }
                )
            }
        )
        discoverFragment?.setOnRvScrollListener(
            OnRvScrollListener {
                binding!!.tb.setBackgroundColor(
                    if (it < 0) {
                        this@MainActivity.changeBgDiscover = true
                        color(R.color.white_50)
                    } else {
                        this@MainActivity.changeBgDiscover = false
                        color(R.color.white)
                    }
                )
            }
        )
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

    inner class LifecycleManager {

        fun requestPermissions() {
            permissionsDisposable = rxPermissions
                .requestEachCombined(
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
                )
                .subscribe { permission ->
                    when {
                        permission.granted -> {
                            downloadSplash()
                        }
                        permission.shouldShowRequestPermissionRationale -> {

                        }
                        else -> {

                        }
                    }
                }
//            XXPermissions.with(this@MainActivity)
//                .permission(Permission.WRITE_EXTERNAL_STORAGE)
//                .permission(Permission.READ_PHONE_STATE)
//                .request(object : OnPermissionCallback {
//                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
//                        if (!allGranted) {
//                            shortToast("获取部分权限成功，但部分权限未正常授予")
//                            return
//                        }
//
//                        shortToast("获取权限成功")
//                    }
//
//                    override fun onDenied(
//                        permissions: MutableList<String>,
//                        doNotAskAgain: Boolean,
//                    ) {
//                        if (doNotAskAgain) {
//                            shortToast("被永久拒绝授权，请手动授予权限")
//                            //如果是被永久拒绝就跳转到应用权限系统设置页面
//                            XXPermissions.startPermissionActivity(this@MainActivity, permissions)
//                        } else {
//                            shortToast("获取权限失败")
//                        }
//                    }
//                })
        }

        fun cancelToast() {
            toast?.cancel()
        }

        fun dispose() {
            permissionsDisposable?.let {
                if (!it.isDisposed) {
                    it.dispose()
                }
            }
        }

    }

}