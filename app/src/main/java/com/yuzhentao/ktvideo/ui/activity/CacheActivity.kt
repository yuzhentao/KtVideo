package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.CacheAdapter
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.interfaces.OnItemClickListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 我的缓存
 */
class CacheActivity : AppCompatActivity(), View.OnClickListener {

    var context: Context = this
    var activity: CacheActivity = this

    var beans = ArrayList<VideoBean>()

    lateinit var adapter: CacheAdapter

    private var disposable: Disposable? = null

    private lateinit var dbManager: VideoDbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
        dbManager = VideoDbManager()
        ImmersionBar.with(activity).transparentBar().barAlpha(0.3F).fitsSystemWindows(true).init()
        initView()
        initData()
    }

    override fun onDestroy() {
        dbManager.close()
        disposable?.let {
            if (!disposable!!.isDisposed) {
                disposable!!.dispose()
            }
        }
        super.onDestroy()
    }

    override fun onClick(v: View?) {

    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.title = getString(R.string.mine_cache)
            bar.setDisplayHomeAsUpEnabled(true)
            tb.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = CacheAdapter(context, beans, dbManager)
        rv.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val bean: VideoBean? = beans[position]
                bean?.let {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelable("data", bean)
                    intent.putExtra("bundle", bundle)
                    context.startActivity(intent)
                }
            }

            override fun onItemLongClick(view: View, position: Int): Boolean {
                return false
            }
        })
    }

    private fun initData() {
        dbManager.findAll(VideoBean::class.java)?.let {
            if (beans.size > 0) {
                beans.clear()
            }
            beans.addAll(it)
            if (beans.size > 0) {
                rv.visibility = View.VISIBLE
                tv_hint.visibility = View.GONE
            } else {
                rv.visibility = View.GONE
                tv_hint.visibility = View.VISIBLE
            }
            adapter.notifyDataSetChanged()
        }
    }

}