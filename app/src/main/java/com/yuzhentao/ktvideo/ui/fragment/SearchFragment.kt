package com.yuzhentao.ktvideo.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.view.*
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.SearchAdapter
import com.yuzhentao.ktvideo.util.CircularRevealAnim
import com.yuzhentao.ktvideo.util.KeyBoardUtil
import kotlinx.android.synthetic.main.fragment_search.*

const val SEARCH_TAG = "SearchFragment"

class SearchFragment : DialogFragment(), View.OnClickListener, ViewTreeObserver.OnPreDrawListener, DialogInterface.OnKeyListener, CircularRevealAnim.AnimListener {

    lateinit var activity: Activity

    lateinit var rootView: View
    lateinit var circularRevealAnim: CircularRevealAnim

    private var data: MutableList<String> = mutableListOf("脱口秀", "城会玩", "666", "笑cry", "漫威",
            "清新", "匠心", "VR", "心理学", "舞蹈", "品牌广告", "粉丝自制", "电影相关", "萝莉", "魔性"
            , "第一视角", "教程", "毕业设计", "奥斯卡", "燃", "冰与火之歌", "温情", "线下campaign", "公益")
    lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity = requireActivity()
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
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
                hideAnim()
            }
            R.id.iv_search -> {
                search()
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
        return false
    }

    override fun onHideAnimationEnd() {
        et.setText("")
        dismiss()
    }

    override fun onShowAnimationEnd() {
        if (isVisible) {
            KeyBoardUtil.openKeyboard(activity, et)
        }
    }

    private fun initView() {
        circularRevealAnim = CircularRevealAnim()
        circularRevealAnim.setAnimListener(this)
        iv_back.setOnClickListener(this)
        iv_search.setOnClickListener(this)
        iv_search.viewTreeObserver.addOnPreDrawListener(this)
        et.requestFocus()
        tv_hot.typeface = Typeface.createFromAsset(activity.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        dialog.setOnKeyListener(this)
        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW//主轴排列方式
        layoutManager.flexWrap = FlexWrap.WRAP//是否换行
        rv.layoutManager = layoutManager
        adapter = SearchAdapter(data)
        rv.adapter = adapter
        rv.itemAnimator = DefaultItemAnimator()
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.98).toInt()
        window!!.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)//取消过渡动画
    }

    private fun hideAnim() {
        KeyBoardUtil.closeKeyboard(activity, et)
        circularRevealAnim.hide(iv_search, rootView)
    }

    private fun search() {
        val searchKey = et.text.toString()
    }

}