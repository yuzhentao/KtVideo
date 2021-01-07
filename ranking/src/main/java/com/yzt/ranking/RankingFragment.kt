package com.yzt.ranking

import android.view.LayoutInflater
import android.view.View
import com.yzt.common.base.BaseFragment
import com.yzt.ranking.databinding.FragmentRankingBinding

/**
 * @author yzt 2020/12/31
 */
class RankingFragment : BaseFragment() {

    private var binding: FragmentRankingBinding? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentRankingBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {

    }

    override fun initData() {

    }

}