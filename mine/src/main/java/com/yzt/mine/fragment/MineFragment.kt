package com.yzt.mine.fragment

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.yzt.common.base.BaseFragment
import com.yzt.common.key.Constant
import com.yzt.common.util.ClickUtil
import com.yzt.mine.R
import com.yzt.mine.databinding.FragmentMineBinding

/**
 * @author yzt 2020/12/31
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentMineBinding? = null

    override fun getLayoutId(): Int? {
        return null
    }

    override fun getLayoutView(inflater: LayoutInflater): View? {
        binding = FragmentMineBinding.inflate(inflater)
        return binding?.root
    }

    override fun init() {

    }

    override fun initView() {
        binding!!.ivAvatar.setOnClickListener(this)
        binding!!.tvLogin.setOnClickListener(this)
        binding!!.llFavorite.setOnClickListener(this)
        binding!!.llComment.setOnClickListener(this)
        binding!!.tvCache.setOnClickListener(this)
        binding!!.tvWatch.setOnClickListener(this)
        binding!!.tvFeedback.setOnClickListener(this)
        binding!!.tvCache.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        binding!!.tvWatch.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        binding!!.tvFeedback.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun initData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_avatar, R.id.tv_login -> {
                if (!ClickUtil.isFastDoubleClick(
                        R.id.iv_avatar,
                        1000
                    ) || !ClickUtil.isFastDoubleClick(R.id.tv_login, 1000)
                ) {
//                    TransitionsHelper.startActivity(
//                        activity,
//                        LoginActivity::class.java,
//                        iv_avatar
//                    )
                }
            }
            R.id.ll_favorite -> {//收藏
                if (!ClickUtil.isFastDoubleClick(R.id.ll_favorite, 1000)) {
                    ARouter.getInstance().build(Constant.PATH_FAVORITE).navigation()
                }
            }
            R.id.ll_comment -> {//评论
                if (!ClickUtil.isFastDoubleClick(R.id.ll_comment, 1000)) {
                    ARouter.getInstance().build(Constant.PATH_COMMENT).navigation()
                }
            }
            R.id.tv_cache -> {//我的缓存
                if (!ClickUtil.isFastDoubleClick(R.id.tv_cache, 1000)) {
                    ARouter.getInstance().build(Constant.PATH_CACHE).navigation()
                }
            }
            R.id.tv_watch -> {//观看记录
                if (!ClickUtil.isFastDoubleClick(R.id.tv_watch, 1000)) {
                    ARouter.getInstance().build(Constant.PATH_WATCH).navigation()
                }
            }
            R.id.tv_feedback -> {//意见反馈
                if (!ClickUtil.isFastDoubleClick(R.id.tv_feedback, 1000)) {
                    ARouter.getInstance().build(Constant.PATH_FEEDBACK).navigation()
                }
            }
        }
    }

}