package com.yuzhentao.ktvideo.ui.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.adapter.CacheAdapter
import com.yuzhentao.ktvideo.bean.VideoBean
import com.yuzhentao.ktvideo.util.ObjectSaveUtils
import com.yuzhentao.ktvideo.util.SPUtils
import com.yuzhentao.ktvideo.util.normalSchedulers
import io.reactivex.Observable
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cache)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        initView()
        initData()
    }

    override fun onDestroy() {
        disposable.let {
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
        adapter = CacheAdapter(context, beans)
        rv.adapter = adapter
    }

    private fun initData() {
        val observable: Observable<ArrayList<VideoBean>> = Observable.create { emitter ->
            val beans = ArrayList<VideoBean>()
            val count = SPUtils.getInstance(context, "downloads").getInt("count")
            var num = 1
            while (num <= count) {
                var bean: VideoBean
                if (ObjectSaveUtils.getValue(context, "download$num") == null) {
                    continue
                } else {
                    bean = ObjectSaveUtils.getValue(context, "download$num") as VideoBean
                }
                beans.add(bean)
                num++
            }
            emitter.onNext(beans)
        }
        disposable = observable.normalSchedulers().subscribe { tempBeans: ArrayList<VideoBean>? ->
            tempBeans?.let {
                if (beans.size > 0) {
                    beans.clear()
                }
                tempBeans.forEach {
                    beans.add(it)
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

}