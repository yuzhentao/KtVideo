package com.yuzhentao.ktvideo.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.RadioButton
import com.yuzhentao.ktvideo.R
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var tvTitle: AppCompatTextView? = null
    private var ivSearch: AppCompatImageView? = null
    private var rbHome: RadioButton? = null
    private var rbDiscover: RadioButton? = null
    private var rbRanking: RadioButton? = null
    private var rbMine: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rb_home -> {
                tvTitle?.text = getToday()
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                rbHome?.isSelected = true
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = false
                rbMine?.isSelected = false
            }
            R.id.rb_discover -> {
                tvTitle?.setText(R.string.discover)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = true
                rbRanking?.isSelected = false
                rbMine?.isSelected = false
            }
            R.id.rb_ranking -> {
                tvTitle?.setText(R.string.ranking)
                tvTitle?.visibility = View.VISIBLE
                ivSearch?.setImageResource(R.drawable.icon_search)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = true
                rbMine?.isSelected = false
            }
            R.id.rb_mine -> {
                tvTitle?.visibility = View.GONE
                ivSearch?.setImageResource(R.drawable.icon_setting)
                rbHome?.isSelected = false
                rbDiscover?.isSelected = false
                rbRanking?.isSelected = false
                rbMine?.isSelected = true
            }
        }
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_center_top)
        ivSearch = findViewById(R.id.iv_right_top)
        rbHome = findViewById(R.id.rb_home)
        rbDiscover = findViewById(R.id.rb_discover)
        rbRanking = findViewById(R.id.rb_ranking)
        rbMine = findViewById(R.id.rb_mine)
        tvTitle?.text = getToday()
        tvTitle?.typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tvTitle?.visibility = View.VISIBLE
        ivSearch?.setImageResource(R.drawable.icon_search)
        rbHome?.isSelected = true
        rbHome?.setOnClickListener(this)
        rbDiscover?.setOnClickListener(this)
        rbRanking?.setOnClickListener(this)
        rbMine?.setOnClickListener(this)
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