package com.yzt.ktvideo.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.key.Constant
import com.yzt.common.util.CircularRevealAnim
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.KeyBoardUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.HotSearchAdapter
import com.yzt.ktvideo.mvp.contract.HotSearchContract
import com.yzt.ktvideo.mvp.presenter.HotSearchPresenter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : DialogFragment(),
    View.OnClickListener,
    ViewTreeObserver.OnPreDrawListener,
    DialogInterface.OnKeyListener,
    CircularRevealAnim.AnimListener,
    HotSearchContract.View {

    val simpleName: String by lazy {
        this::class.java.simpleName
    }

    private lateinit var activity: Activity

    private lateinit var rootView: View

    private val adapter: HotSearchAdapter by lazy {
        HotSearchAdapter(null)
    }

    private val presenter: HotSearchPresenter by lazy {
        HotSearchPresenter(context!!, this)
    }

    private val circularRevealAnim: CircularRevealAnim by lazy {
        CircularRevealAnim()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = requireActivity()
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            fitsSystemWindows(true)
        }
        initView()
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_back, 1000)) {
                    hideAnim()
                }
            }
            R.id.iv_search -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_search, 1000)) {
                    search()
                }
            }
        }
    }

    override fun onPreDraw(): Boolean {
        iv_search.viewTreeObserver.removeOnPreDrawListener(this)
        circularRevealAnim.show(iv_search, rootView)
        return true
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP) {
            hideAnim()
        } else if (keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN) {
            search()
        }
        return true
    }

    override fun onHideAnimationEnd() {
        et.setText("")
        dismiss()
    }

    override fun onShowAnimationEnd() {
        if (isVisible) {
            KeyBoardUtil.openKeyboard(context, et)
        }
    }

    override fun setData(beans: MutableList<String>?) {
        adapter.setList(beans)
    }

    private fun initView() {
        context?.let {
            presenter.load()
            circularRevealAnim.setAnimListener(this)
            iv_back.setOnClickListener(this)
            iv_search.setOnClickListener(this)
            iv_search.viewTreeObserver.addOnPreDrawListener(this)
            et.requestFocus()
            tv_hot.typeface =
                Typeface.createFromAsset(activity.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
            dialog?.setOnKeyListener(this)
            val layoutManager = FlexboxLayoutManager(it)
            layoutManager.flexDirection = FlexDirection.ROW//主轴排列方式
            layoutManager.flexWrap = FlexWrap.WRAP//是否换行
            rv.layoutManager = layoutManager
            rv.adapter = adapter
            rv.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            adapter.setOnItemClickListener { adapter, _, position ->
                val bean: String? = adapter.data[position] as String
                bean?.let { itt ->
                    KeyBoardUtil.closeKeyboard(it, et)
                    ARouter
                        .getInstance()
                        .build(Constant.PATH_WATCH)
                        .withString("key", itt)
                        .navigation()
                }
            }
        }
    }

    private fun initDialog() {
        val window = dialog?.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)//取消过渡动画
    }

    private fun hideAnim() {
        KeyBoardUtil.closeKeyboard(context, et)
        circularRevealAnim.hide(iv_search, rootView)
    }

    private fun search() {
        val key = et.text.toString()
        if (key.isNotEmpty()) {
            KeyBoardUtil.closeKeyboard(context, et)
            ARouter
                .getInstance()
                .build(Constant.PATH_WATCH)
                .withString("key", key)
                .navigation()
        }
    }

}