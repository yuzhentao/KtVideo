package com.yzt.ranking

import android.os.Bundle
import android.view.View
import com.yzt.common.base.BaseActivity
import com.yzt.ranking.databinding.ActivityRankingBinding

/**
 * @author yzt 2020/12/31
 */
class RankingActivity : BaseActivity() {

    private var binding: ActivityRankingBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityRankingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}