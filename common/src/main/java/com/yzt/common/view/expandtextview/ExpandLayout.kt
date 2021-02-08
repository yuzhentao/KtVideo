package com.yzt.common.view.expandtextview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.yzt.common.R
import com.yzt.common.extension.color
import com.yzt.common.util.DimenUtil
import kotlinx.android.synthetic.main.layout_expand_view.view.*

class ExpandLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var listener: OnExpandListener? = null

    private var contentTextSize = 18F
    private var contentTextColor = 0
    private var maxCollapsedLines = 3
    private var ellipsizeText = "..."
    private var expandText = ""
    private var collapseText = ""
    private var expandCollapseTextSize = 18F
    private var expandCollapseTextColor = 0
    private var expandCollapseTextGravity = 0
    private var expandCollapseTextLayoutGravity = 0
    private var expandCollapseTextMarginTop = 0F

    init {
        initAttrs(context, attrs)
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.layout_expand_view, this)
//        etv_content.textSize = DimenUtil.px2sp(context, contentTextSize).toFloat()
        etv_content.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize)
        etv_content.setTextColor(contentTextColor)
        etv_content.setMaxLineCount(maxCollapsedLines)
        etv_content.setEllipsizeText(ellipsizeText)
//        tv_tip.textSize = DimenUtil.px2sp(context, expandCollapseTextSize).toFloat()
        tv_tip.setTextSize(TypedValue.COMPLEX_UNIT_PX, expandCollapseTextSize)
        tv_tip.setTextColor(expandCollapseTextColor)
        tv_tip.gravity = when (expandCollapseTextGravity) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            2 -> Gravity.END
            else -> Gravity.START
        }
        tv_tip.post {
            val lp = tv_tip.layoutParams as LayoutParams
//            lp.topMargin = expandCollapseTextMarginTop.toInt()
            lp.topMargin = -tv_tip.height
            lp.gravity = when (expandCollapseTextLayoutGravity) {
                0 -> Gravity.START
                1 -> Gravity.CENTER
                2 -> Gravity.END
                else -> Gravity.START
            }
            tv_tip.layoutParams = lp
            etv_content.requestLayout()
            tv_tip.requestLayout()
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout)
        contentTextSize = ta.getDimension(R.styleable.ExpandLayout_contentTextSize, DimenUtil.sp2px(context, 18F).toFloat())
        contentTextColor = ta.getColor(R.styleable.ExpandLayout_contentTextColor, context.color(R.color.app_black))
        maxCollapsedLines = ta.getInt(R.styleable.ExpandLayout_maxCollapsedLines, 3)
        ellipsizeText = if (ta.getString(R.styleable.ExpandLayout_ellipsizeText).isNullOrEmpty()) "..." else ta.getString(R.styleable.ExpandLayout_ellipsizeText)!!
        expandText = if (ta.getString(R.styleable.ExpandLayout_expandText).isNullOrEmpty()) "更多" else ta.getString(R.styleable.ExpandLayout_expandText)!!
        collapseText = if (ta.getString(R.styleable.ExpandLayout_collapseText).isNullOrEmpty()) "" else ta.getString(R.styleable.ExpandLayout_collapseText)!!
        expandCollapseTextSize = ta.getDimension(R.styleable.ExpandLayout_expandCollapseTextSize, DimenUtil.sp2px(context, 18F).toFloat())
        expandCollapseTextColor = ta.getColor(R.styleable.ExpandLayout_expandCollapseTextColor, context.color(R.color.app_pink))
        expandCollapseTextGravity = ta.getInt(R.styleable.ExpandLayout_expandCollapseTextGravity, 0)
        expandCollapseTextLayoutGravity = ta.getInt(R.styleable.ExpandLayout_expandCollapseTextLayoutGravity, 0)
        expandCollapseTextMarginTop = ta.getDimension(R.styleable.ExpandLayout_expandCollapseTextMarginTop, 0f)
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