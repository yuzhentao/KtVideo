package com.yzt.common.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * @author yzt 2020/12/31
 */
abstract class BaseFragment : Fragment() {

    protected var activity: BaseActivity? = null
    protected var app: App? = null

    private var rootView: View? = null

    protected var TAG: String? = null
    private var isFirst = true //是否是第一次加载

    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            app = App.app
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = App.app
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getLayoutId()?.let {
            if (rootView == null) {
                rootView = inflater.inflate(it, container, false)
            }
            rootView!!.parent?.let {
                (rootView!!.parent as ViewGroup).removeView(rootView)
            }
            return rootView
        }
        getLayoutView()?.let {
            if (rootView == null) {
                rootView = it
            }
            rootView!!.parent?.let {
                (rootView!!.parent as ViewGroup).removeView(rootView)
            }
            return rootView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        TAG = this.javaClass.simpleName
        activity = getActivity() as BaseActivity?
        init()
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (isLazyLoad()) {
            if (isFirst) {
                isFirst = false
                initData()
            }
        } else {
            initData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        app = null
    }

    /**
     * 使用FragmentTransaction来控制时建议设为false
     */
    protected open fun isLazyLoad(): Boolean {
        return true
    }

    protected open fun isActivityExist(): Boolean {
        return activity != null && !activity!!.isFinishing
    }

    protected open fun hasPermissions(vararg permissions: String?): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context!!,
                    permission!!
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    protected abstract fun getLayoutId(): Int?

    protected abstract fun getLayoutView(): View?

    protected abstract fun init()

    protected abstract fun initView()

    protected abstract fun initData()

}