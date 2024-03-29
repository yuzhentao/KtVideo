package com.yzt.ktvideo.ui.fragment

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.util.CircularRevealAnim
import com.yzt.common.util.ClickUtil
import com.yzt.common.util.KeyBoardUtil
import com.yzt.common.util.ToastUtil
import com.yzt.ktvideo.R
import com.yzt.ktvideo.adapter.HotSearchAdapter
import com.yzt.ktvideo.databinding.FragmentSearchBinding
import com.yzt.ktvideo.viewmodel.HotSearchViewModel
import com.yzt.ktvideo.viewmodel.HotSearchViewModelFactory

/**
 * 搜索
 *
 * @author yzt 2021/2/9
 */
class SearchFragment : DialogFragment(),
    View.OnClickListener,
    ViewTreeObserver.OnPreDrawListener,
    DialogInterface.OnKeyListener,
    CircularRevealAnim.AnimListener {

    private var binding: FragmentSearchBinding? = null

    val simpleName: String by lazy {
        this::class.java.simpleName
    }

    private lateinit var activity: Activity

    private var rootView: View? = null

    private val adapter: HotSearchAdapter by lazy {
        HotSearchAdapter(null)
    }

    private val viewModel: HotSearchViewModel by lazy {
        ViewModelProvider(this, HotSearchViewModelFactory())[HotSearchViewModel::class.java]
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
        binding = FragmentSearchBinding.inflate(inflater)
        rootView = binding?.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        immersionBar {
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
        binding!!.ivSearch.viewTreeObserver.removeOnPreDrawListener(this)
        rootView?.let {
            circularRevealAnim.show(binding!!.ivSearch, it)
        }
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
        binding!!.et.setText("")
        dismiss()
    }

    override fun onShowAnimationEnd() {
        if (isVisible) {
            KeyBoardUtil.openKeyboard(context, binding!!.et)
        }
    }

    private fun initView() {
        context?.let {
            viewModel.load(requireContext())
            circularRevealAnim.setAnimListener(this)
            binding!!.ivBack.setOnClickListener(this)
            binding!!.ivBack.setOnLongClickListener { true }
            binding!!.ivSearch.setOnClickListener(this)
            binding!!.ivSearch.setOnLongClickListener { true }
            binding!!.ivSearch.viewTreeObserver.addOnPreDrawListener(this)
            binding!!.et.requestFocus()
            binding!!.tvHot.typeface =
                Typeface.createFromAsset(activity.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
            dialog?.setOnKeyListener(this)
            val layoutManager = FlexboxLayoutManager(it)
            layoutManager.flexDirection = FlexDirection.ROW//主轴排列方式
            layoutManager.flexWrap = FlexWrap.WRAP//是否换行
            binding!!.rv.layoutManager = layoutManager
            binding!!.rv.adapter = adapter
            binding!!.rv.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            adapter.setOnItemClickListener { adapter, _, position ->
                val bean: String? = adapter.data[position] as String?
                bean?.let { itt ->
                    KeyBoardUtil.closeKeyboard(it, binding!!.et)
                    ToastUtil.shortCenter(R.string.api_no_response)
                }
            }
            viewModel.liveData.observe(
                this,
                Observer<MutableList<String>> { beans ->
                    adapter.setList(beans)
                }
            )
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
        KeyBoardUtil.closeKeyboard(context, binding!!.et)
        rootView?.let {
            circularRevealAnim.hide(binding!!.ivSearch, it)
        }
    }

    private fun search() {
        val key = binding!!.et.text.toString()
        if (key.isNotEmpty()) {
            KeyBoardUtil.closeKeyboard(context, binding!!.et)
            ToastUtil.shortCenter(R.string.api_no_response)
        }
    }

}