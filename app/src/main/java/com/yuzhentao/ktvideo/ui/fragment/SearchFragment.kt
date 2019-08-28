package com.yuzhentao.ktvideo.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.HotSearchAdapter
import com.yuzhentao.ktvideo.mvp.contract.HotSearchContract
import com.yuzhentao.ktvideo.mvp.presenter.HotSearchPresenter
import com.yuzhentao.ktvideo.ui.activity.WatchActivity
import com.yuzhentao.ktvideo.util.CircularRevealAnim
import com.yuzhentao.ktvideo.util.ClickUtil
import com.yuzhentao.ktvideo.util.KeyBoardUtil
import kotlinx.android.synthetic.main.fragment_search.*

const val SEARCH_TAG = "SearchFragment"

class SearchFragment : androidx.fragment.app.DialogFragment(),
    View.OnClickListener,
    ViewTreeObserver.OnPreDrawListener,
    DialogInterface.OnKeyListener,
    CircularRevealAnim.AnimListener,
    HotSearchContract.View {

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
        adapter.setNewData(beans)
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
            dialog.setOnKeyListener(this)
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW//主轴排列方式
            layoutManager.flexWrap = FlexWrap.WRAP//是否换行
            rv.layoutManager = layoutManager
            rv.adapter = adapter
            rv.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            adapter.onItemClickListener =
                BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
                    val bean: String? = adapter!!.data[position] as String
                    bean?.let {
                        KeyBoardUtil.closeKeyboard(context, et)
                        val intent = Intent(context, WatchActivity::class.java)
                        intent.putExtra("key", bean)
                        context?.startActivity(intent)
                    }
                }
        }
    }

    private fun initDialog() {
        val window = dialog.window
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
            val intent = Intent(context, WatchActivity::class.java)
            intent.putExtra("key", key)
            context?.startActivity(intent)
        }
    }

}