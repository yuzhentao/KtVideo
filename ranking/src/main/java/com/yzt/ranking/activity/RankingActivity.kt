package com.yzt.ranking.activity

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseAppCompatActivity
import com.yzt.ranking.databinding.ActivityRankingBinding

/**
 * 排行
 *
 * @author yzt 2020/12/31
 */
class RankingActivity : BaseAppCompatActivity() {

    private var binding: ActivityRankingBinding? = null

    override fun initBeforeSetLayout(savedInstanceState: Bundle?) {

    }

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityRankingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun initAfterSetLayout(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initLifecycleObserver() {

    }

}