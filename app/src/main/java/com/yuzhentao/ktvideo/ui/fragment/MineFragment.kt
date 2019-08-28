package com.yuzhentao.ktvideo.ui.fragment

import android.content.Intent
import android.graphics.Typeface
import android.view.View
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.ui.activity.*
import com.yuzhentao.transitionhelper.TransitionsHelper
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
                TransitionsHelper.startActivity(
                    activity,
                    LoginActivity::class.java,
                    iv_avatar
                )
            }
            R.id.ll_favorite -> {
                startActivity(Intent(activity, FavoriteActivity::class.java))
            }
            R.id.ll_comment -> {
                startActivity(Intent(activity, CommentActivity::class.java))
            }
            R.id.tv_cache -> {
                startActivity(Intent(activity, CacheActivity::class.java))
            }
            R.id.tv_watch -> {
                startActivity(Intent(activity, WatchActivity::class.java))
            }
            R.id.tv_feedback -> {
                startActivity(Intent(activity, FeedbackActivity::class.java))
            }
        }
    }

}