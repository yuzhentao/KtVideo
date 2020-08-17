package com.yzt.ktvideo.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.yzt.ktvideo.R
import com.yzt.ktvideo.ui.activity.*
import com.yzt.ktvideo.util.ClickUtil
import com.yzt.transitionhelper.TransitionsHelper
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * 我的
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    private lateinit var activity: MainActivity

    override fun getLayoutResources(): Int {
        activity = getActivity() as MainActivity
        return R.layout.fragment_mine
    }

    override fun initView() {
        iv_avatar.setOnClickListener(this)
        tv_login.setOnClickListener(this)
        ll_favorite.setOnClickListener(this)
        ll_comment.setOnClickListener(this)
        tv_cache.setOnClickListener(this)
        tv_watch.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)
        tv_cache.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_watch.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_feedback.typeface =
            Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun onFragmentVisibleChange(b: Boolean) {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_avatar, R.id.tv_login -> {
                if (!ClickUtil.isFastDoubleClick(
                        R.id.iv_avatar,
                        1000
                    ) || !ClickUtil.isFastDoubleClick(R.id.tv_login, 1000)
                ) {
                    TransitionsHelper.startActivity(
                        activity,
                        LoginActivity::class.java,
                        iv_avatar
                    )
                }
            }
            R.id.ll_favorite -> {
                if (!ClickUtil.isFastDoubleClick(R.id.ll_favorite, 1000)) {
                    startActivity(Intent(activity, FavoriteActivity::class.java))
                }
            }
            R.id.ll_comment -> {
                if (!ClickUtil.isFastDoubleClick(R.id.ll_comment, 1000)) {
                    startActivity(Intent(activity, CommentActivity::class.java))
                }
            }
            R.id.tv_cache -> {
                if (!ClickUtil.isFastDoubleClick(R.id.tv_cache, 1000)) {
                    startActivity(Intent(activity, CacheActivity::class.java))
                }
            }
            R.id.tv_watch -> {
                if (!ClickUtil.isFastDoubleClick(R.id.tv_watch, 1000)) {
                    startActivity(Intent(activity, WatchActivity::class.java))
                }
            }
            R.id.tv_feedback -> {
                if (!ClickUtil.isFastDoubleClick(R.id.tv_feedback, 1000)) {
                    startActivity(Intent(activity, FeedbackActivity::class.java))
                }
            }
        }
    }

}