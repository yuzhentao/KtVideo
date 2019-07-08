package com.yuzhentao.ktvideo.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.LinearLayout
import com.yuzhentao.ktvideo.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var tvTitle: AppCompatTextView? = null
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
                tvTitle?.setText(R.string.home_bottom_1)
                tvTitle?.visibility = View.VISIBLE
                llHome?.isSelected = true
                llFind?.isSelected = false
                llHot?.isSelected = false
                llMine?.isSelected = false
            }
            R.id.ll_find -> {
                tvTitle?.setText(R.string.home_bottom_2)
                tvTitle?.visibility = View.VISIBLE
                llHome?.isSelected = false
                llFind?.isSelected = true
                llHot?.isSelected = false
                llMine?.isSelected = false
            }
            R.id.ll_hot -> {
                tvTitle?.setText(R.string.home_bottom_3)
                tvTitle?.visibility = View.VISIBLE
                llHome?.isSelected = false
                llFind?.isSelected = false
                llHot?.isSelected = true
                llMine?.isSelected = false
            }
            R.id.ll_mine -> {
                tvTitle?.setText(R.string.home_bottom_4)
                tvTitle?.visibility = View.GONE
                llHome?.isSelected = false
                llFind?.isSelected = false
                llHot?.isSelected = false
                llMine?.isSelected = true
            }
        }
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_center_top)
        llHome = findViewById(R.id.ll_home)
        llFind = findViewById(R.id.ll_find)
        llHot = findViewById(R.id.ll_hot)
        llMine = findViewById(R.id.ll_mine)
        tvTitle?.setText(R.string.home_bottom_1)
        tvTitle?.visibility = View.VISIBLE
        llHome?.isSelected = true
        llHome?.setOnClickListener(this)
        llFind?.setOnClickListener(this)
        llHot?.setOnClickListener(this)
        llMine?.setOnClickListener(this)
    }

}