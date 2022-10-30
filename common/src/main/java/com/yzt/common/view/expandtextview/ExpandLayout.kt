package com.yzt.common.view.expandtextview

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.yzt.common.R
import com.yzt.common.extension.color
import com.yzt.common.util.DimenUtil

class ExpandLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var llExpandView: LinearLayout? = null
    private var etvContent: ExpandTextView? = null
    private var tvTip: AppCompatTextView? = null

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
        val view = LayoutInflater.from(context).inflate(R.layout.layout_expand_view, this)
        llExpandView = view.findViewById(R.id.ll_expand_view)
        etvContent = view.findViewById(R.id.etv_content)
        tvTip = view.findViewById(R.id.tv_tip)
//        etvContent!!.textSize = DimenUtil.px2sp(context, contentTextSize).toFloat()
        etvContent!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize)
        etvContent!!.setTextColor(contentTextColor)
        etvContent!!.setMaxLineCount(maxCollapsedLines)
        etvContent!!.setEllipsizeText(ellipsizeText)
//        tvTip!!.textSize = DimenUtil.px2sp(context, expandCollapseTextSize).toFloat()
        tvTip!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, expandCollapseTextSize)
        tvTip!!.setTextColor(expandCollapseTextColor)
        tvTip!!.gravity = when (expandCollapseTextGravity) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            2 -> Gravity.END
            else -> Gravity.START
        }
        tvTip!!.post {
            val lp = tvTip!!.layoutParams as LayoutParams
//            lp.topMargin = expandCollapseTextMarginTop.toInt()
            lp.topMargin = -tvTip!!.height
            lp.gravity = when (expandCollapseTextLayoutGravity) {
                0 -> Gravity.START
                1 -> Gravity.CENTER
                2 -> Gravity.END
                else -> Gravity.START
            }
            tvTip!!.layoutParams = lp
            etvContent!!.requestLayout()
            tvTip!!.requestLayout()
        }
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandLayout)
        contentTextSize = ta.getDimension(
            R.styleable.ExpandLayout_contentTextSize,
            DimenUtil.sp2px(context, 18F).toFloat()
        )
        contentTextColor =
            ta.getColor(R.styleable.ExpandLayout_contentTextColor, context.color(R.color.app_black))
        maxCollapsedLines = ta.getInt(R.styleable.ExpandLayout_maxCollapsedLines, 3)
        ellipsizeText = if (ta.getString(R.styleable.ExpandLayout_ellipsizeText)
                .isNullOrEmpty()
        ) "..." else ta.getString(R.styleable.ExpandLayout_ellipsizeText)!!
        expandText = if (ta.getString(R.styleable.ExpandLayout_expandText)
                .isNullOrEmpty()
        ) "更多" else ta.getString(R.styleable.ExpandLayout_expandText)!!
        collapseText = if (ta.getString(R.styleable.ExpandLayout_collapseText)
                .isNullOrEmpty()
        ) "" else ta.getString(R.styleable.ExpandLayout_collapseText)!!
        expandCollapseTextSize = ta.getDimension(
            R.styleable.ExpandLayout_expandCollapseTextSize,
            DimenUtil.sp2px(context, 18F).toFloat()
        )
        expandCollapseTextColor = ta.getColor(
            R.styleable.ExpandLayout_expandCollapseTextColor,
            context.color(R.color.app_pink)
        )
        expandCollapseTextGravity = ta.getInt(R.styleable.ExpandLayout_expandCollapseTextGravity, 0)
        expandCollapseTextLayoutGravity =
            ta.getInt(R.styleable.ExpandLayout_expandCollapseTextLayoutGravity, 0)
        expandCollapseTextMarginTop =
            ta.getDimension(R.styleable.ExpandLayout_expandCollapseTextMarginTop, 0f)
        ta.recycle()
    }

    fun setText(text: String, expand: Boolean, listener: OnExpandListener) {
        this.listener = listener
        llExpandView!!.setOnClickListener {
            this.listener?.expandChange()
        }
        etvContent!!.setChanged(expand)
        etvContent!!.setText(text, expand, object : ExpandTextView.Callback {
            override fun onExpand() {
                tvTip!!.visibility = View.VISIBLE
                tvTip!!.text = collapseText
            }

            override fun onCollapse() {
                tvTip!!.visibility = View.VISIBLE
                tvTip!!.text = expandText
            }

            override fun onLoss() {
                tvTip!!.visibility = View.GONE
            }
        })
    }

    interface OnExpandListener {

        fun expandChange()

    }

}