package com.yzt.ktvideo.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mmkv.MMKV
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.common.key.Keys
import com.yzt.common.util.ClickUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.databinding.ActivitySettingBinding

/**
 * 设置
 *
 * @author yzt 2021/2/9
 */
class SettingActivity : BaseAppCompatActivity(), View.OnClickListener {

    private var binding: ActivitySettingBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.ivTop.setOnClickListener(this)
        binding!!.ivTop.setOnLongClickListener { true }
        binding!!.tvTop.text = getString(R.string.mine_setting)
        val mmkv = MMKV.defaultMMKV()
        when (mmkv.decodeInt(Keys.AUTO_PLAY_MODE)) {
            0 -> {
                binding!!.layoutSettingAutoPlay.cbOpen.isChecked = false
                binding!!.layoutSettingAutoPlay.cbOpenInWifi.isChecked = false
                binding!!.layoutSettingAutoPlay.cbClose.isChecked = true
            }
            1 -> {
                binding!!.layoutSettingAutoPlay.cbOpen.isChecked = true
                binding!!.layoutSettingAutoPlay.cbOpenInWifi.isChecked = false
                binding!!.layoutSettingAutoPlay.cbClose.isChecked = false
            }
            2 -> {
                binding!!.layoutSettingAutoPlay.cbOpen.isChecked = false
                binding!!.layoutSettingAutoPlay.cbOpenInWifi.isChecked = true
                binding!!.layoutSettingAutoPlay.cbClose.isChecked = false
            }
        }
        binding!!.layoutSettingAutoPlay.cbOpen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mmkv.encode(Keys.AUTO_PLAY_MODE, 1)
                binding!!.layoutSettingAutoPlay.cbOpenInWifi.isChecked = false
                binding!!.layoutSettingAutoPlay.cbClose.isChecked = false
            }
        }
        binding!!.layoutSettingAutoPlay.cbOpenInWifi.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mmkv.encode(Keys.AUTO_PLAY_MODE, 2)
                binding!!.layoutSettingAutoPlay.cbOpen.isChecked = false
                binding!!.layoutSettingAutoPlay.cbClose.isChecked = false
            }
        }
        binding!!.layoutSettingAutoPlay.cbClose.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mmkv.encode(Keys.AUTO_PLAY_MODE, 0)
                binding!!.layoutSettingAutoPlay.cbOpen.isChecked = false
                binding!!.layoutSettingAutoPlay.cbOpenInWifi.isChecked = false
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initLifecycleObserver() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }

}