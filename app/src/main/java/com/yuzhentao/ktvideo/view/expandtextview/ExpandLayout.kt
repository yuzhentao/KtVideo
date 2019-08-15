package com.yuzhentao.ktvideo.view.expandtextview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.yuzhentao.ktvideo.R
import com.yuzhentao.ktvideo.util.DimenUtil
import kotlinx.android.synthetic.main.layout_expand_view.view.*

class ExpandLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var listener: OnExpandListener? = null

    private var maxCollapsedLines = 3
    private var contentTextSize = 18F
    private var contentTextColor = 0
    private var expandText = ""
    private var collapseText = ""
    private var expandCollapseTextSize = 18F
    private var expandCollapseTextColor = 0
    private var expandCollapseTextGravity = 0
    private var expandCollapseTextLayoutGravity = 0
    private var ellipsizeText = "..."
    private var middlePadding = 0F

    init {
        initAttrs(context, attrs)
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.layout_expand_view, this)
        etv_content.setMaxLineCount(maxCollapsedLines)
        etv_content.textSize = DimenUtil.px2sp(context, contentTextSize).toFloat()
        etv_content.setTextColor(contentTextColor)
        etv_content.setEllipsizeText(ellipsizeText)
        val lp = tv_tip.layoutParams as LayoutParams
        lp.topMargin = middlePadding.toInt()
        lp.gravity = when (expandCollapseTextLayoutGravity) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            2 -> Gravity.END
            else -> Gravity.START
        }
        tv_tip.layoutParams = lp
        tv_tip.textSize = DimenUtil.px2sp(context, expandCollapseTextSize).toFloat()
        tv_tip.setTextColor(expandCollapseTextColor)
        tv_tip.gravity = when (expandCollapseTextGravity) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            2 -> Gravity.END
            else -> Gravity.START
        }
        etv_content.requestLayout()
        tv_tip.requestLayout()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout)
        maxCollapsedLines = ta.getInt(R.styleable.ExpandLayout_maxCollapsedLines, 3)
        contentTextSize = ta.getDimension(R.styleable.ExpandLayout_contentTextSize, DimenUtil.sp2px(context, 18F).toFloat())
        contentTextColor = ta.getColor(R.styleable.ExpandLayout_contentTextColor, ContextCompat.getColor(context, R.color.text_black))
        expandText = if (ta.getString(R.styleable.ExpandLayout_expandText).isNullOrEmpty()) "全文" else ta.getString(R.styleable.ExpandLayout_expandText)!!
        collapseText = if (ta.getString(R.styleable.ExpandLayout_collapseText).isNullOrEmpty()) "收起" else ta.getString(R.styleable.ExpandLayout_collapseText)!!
        expandCollapseTextSize = ta.getDimension(R.styleable.ExpandLayout_expandCollapseTextSize, DimenUtil.sp2px(context, 18F).toFloat())
        expandCollapseTextColor = ta.getColor(R.styleable.ExpandLayout_expandCollapseTextColor, ContextCompat.getColor(context, R.color.text_blue))
        expandCollapseTextGravity = ta.getInt(R.styleable.ExpandLayout_expandCollapseTextGravity, 0)
        expandCollapseTextLayoutGravity = ta.getInt(R.styleable.ExpandLayout_expandCollapseTextLayoutGravity, 0)
        ellipsizeText = if (ta.getString(R.styleable.ExpandLayout_ellipsizeText).isNullOrEmpty()) "..." else ta.getString(R.styleable.ExpandLayout_ellipsizeText)!!
        middlePadding = ta.getDimension(R.styleable.ExpandLayout_middlePadding, 0f)
        ta.recycle()
    }

    fun setText(text: String, expand: Boolean, listener: OnExpandListener) {
        this.listener = listener
        ll_expand_view.setOnClickListener {
            this.listener?.expandChange()
        }
        etv_content.setChanged(expand)
        etv_content.setText(text, expand, object : ExpandTextView.Callback {
            override fun onExpand() {
                tv_tip.visibility = View.VISIBLE
                tv_tip.text = collapseText
            }

            override fun onCollapse() {
                tv_tip.visibility = View.VISIBLE
                tv_tip.text = expandText
            }

            override fun onLoss() {
                tv_tip.visibility = View.GONE
            }
        })
    }

    interface OnExpandListener {

        fun expandChange()

    }

}