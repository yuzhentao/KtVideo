package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.immersionbar.ktx.immersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.WatchAdapter
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.db.VideoDbManager
import com.yuzhentao.ktvideo.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.activity_cache.*

/**
 * 观看记录
 */
class WatchActivity : AppCompatActivity() {

    var context: Context = this
    var activity: WatchActivity = this

    var beans = ArrayList<VideoBean>()

    lateinit var adapter: WatchAdapter

    private lateinit var dbManager: VideoDbManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immersionBar {
            statusBarColor(R.color.black)
            navigationBarColor(R.color.white_50)
            navigationBarDarkIcon(true)
            fitsSystemWindows(true)
        }
        setContentView(R.layout.activity_watch)
        dbManager = VideoDbManager()
        initView()
        initData()
    }

    override fun onDestroy() {
        dbManager.close()
        super.onDestroy()
    }

    private fun initView() {
        setSupportActionBar(tb)
        val bar = supportActionBar
        bar?.let {
            bar.title = getString(R.string.mine_watch)
            bar.setDisplayHomeAsUpEnabled(true)
            tb.setNavigationOnClickListener {
                onBackPressed()
            }
        }
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = WatchAdapter(context, beans)
        rv.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val bean: VideoBean? = beans[position]
                bean?.let {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    val bundle = Bundle()
                    bundle.putParcelable("data", bean)
                    intent.putExtra("bundle", bundle)
                    intent.putExtra("showCache", false)
                    context.startActivity(intent)
                }
            }

            override fun onItemLongClick(view: View, position: Int): Boolean {
                return false
            }
        })
    }

    private fun initData() {
        dbManager.findAll()?.let {
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