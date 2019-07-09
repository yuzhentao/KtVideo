package com.yuzhentao.ktvideo.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.LinearLayout
import com.yuzhentao.ktvideo.R
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var tvTitle: AppCompatTextView? = null
    private var ivSearch: AppCompatImageView? = null
    private var llHome: LinearLayout? = null
    private var llFind: LinearLayout? = null
    private var llHot: LinearLayout? = null
    private var llMine: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_home -> {
                tvTitle?.text = getToday()
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                llHome?.isSelected = true
                llFind?.isSelected = false
                llHot?.isSelected = false
                llMine?.isSelected = false
            }
            R.id.ll_find -> {
                tvTitle?.setText(R.string.discover)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                llHome?.isSelected = false
                llFind?.isSelected = true
                llHot?.isSelected = false
                llMine?.isSelected = false
            }
            R.id.ll_hot -> {
                tvTitle?.setText(R.string.ranking)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                llHome?.isSelected = false
                llFind?.isSelected = false
                llHot?.isSelected = true
                llMine?.isSelected = false
            }
            R.id.ll_mine -> {
                tvTitle?.visibility = View.GONE
                ivSearch?.setImageResource(R.drawable.icon_setting)
                llHome?.isSelected = false
                llFind?.isSelected = false
                llHot?.isSelected = false
                llMine?.isSelected = true
            }
        }
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_center_top)
        ivSearch = findViewById(R.id.iv_right_top)
        llHome = findViewById(R.id.ll_home)
        llFind = findViewById(R.id.ll_find)
        llHot = findViewById(R.id.ll_hot)
        llMine = findViewById(R.id.ll_mine)
        tvTitle?.text = getToday()
        tvTitle?.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tvTitle?.visibility = View.VISIBLE
        ivSearch?.setImageResource(R.drawable.icon_search)
        llHome?.isSelected = true
        llHome?.setOnClickListener(this)
        llFind?.setOnClickListener(this)
        llHot?.setOnClickListener(this)
        llMine?.setOnClickListener(this)
    }

    private fun getToday(): String {
        val week = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        val date = Date()
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return week[index]
    }

}