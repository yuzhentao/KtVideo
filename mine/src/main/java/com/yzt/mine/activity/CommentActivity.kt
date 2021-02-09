package com.yzt.mine.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.yzt.common.base.BaseActivity
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.mine.R
import com.yzt.mine.databinding.ActivityCommentBinding

/**
 * 评论
 *
 * @author yzt 2021/2/9
 */
@Route(path = Constant.PATH_COMMENT)
class CommentActivity : BaseActivity(), View.OnClickListener {

    private var binding: ActivityCommentBinding? = null

    override fun setLayoutId(): Int? {
        return null
    }

    override fun setLayoutView(): View? {
        binding = ActivityCommentBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun init(savedInstanceState: Bundle?) {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding!!.tvTop.text = getString(R.string.mine_comment)
        binding!!.ivTop.setOnClickListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_top -> {
                if (!ClickUtil.isFastDoubleClick(R.id.iv_top, 1000)) {
                    onBackPressed()
                }
            }
        }
    }

}