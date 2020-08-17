package com.yzt.ktvideo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : androidx.fragment.app.Fragment() {

    var isFirst: Boolean = false
    var rootView: View? = null
    var isShow: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResources(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isShow = true
        }
        if (rootView == null) {
            return
        }
        if (!isFirst && isVisible) {
            onFragmentVisibleChange(true)
            return
        }
        if (isVisible) {
            onFragmentVisibleChange(false)
            isShow = false
        }
    }

    abstract fun getLayoutResources(): Int

    abstract fun initView()

    abstract fun onFragmentVisibleChange(b: Boolean)

}