package com.yuzhentao.ktvideo.view.expandtextview

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet

class ExpandTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    /**
     * true：展开，false：收起
     */
    private var mExpanded: Boolean = false

    private var mCallback: Callback? = null

    /**
     * 源文字内容
     */
    private var mText: String = ""
    private var maxLineCount: Int = 3
    private var ellipsizeText = "..."

    fun setMaxLineCount(maxLineCount: Int) {
        this.maxLineCount = maxLineCount
    }

    fun setEllipsizeText(ellipsizeText: String) {
        this.ellipsizeText = ellipsizeText
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sl = StaticLayout(mText, paint, measuredWidth - paddingLeft - paddingRight, Layout.Alignment.ALIGN_CENTER, 1F, 0F, true)
        /**
         * 总计行数
         */
        var lineCount = sl.lineCount
        if (lineCount > maxLineCount) {
            when (mExpanded) {
                true -> {
                    mCallback?.onExpand()
                    text = mText
                }
                false -> {
                    mCallback?.onCollapse()

                    lineCount = maxLineCount
                    //省略文字的宽度
                    val dotWidth = paint.measureText(ellipsizeText)
                    val start = sl.getLineStart(lineCount - 1)
                    val end = sl.getLineEnd(lineCount - 1)
                    val lineText = mText.substring(start, end)
                    var endIndex = 0
                    for (i in lineText.length - 1 downTo 0) {
                        val str = lineText.substring(i, lineText.length)
                        if (paint.measureText(str) >= dotWidth) {
                            endIndex = i
                            break
                        }
                    }
                    val newEndLineText = lineText.substring(0, endIndex) + ellipsizeText
                    text = mText.substring(0, start) + newEndLineText
                }
            }
        } else {
            mCallback?.onLoss()
            text = mText
        }
        var lineHeight = 0
        for (i in 0 until lineCount) {
            val lienBound = Rect()
            sl.getLineBounds(i, lienBound)
            lineHeight += lienBound.height()
        }
        lineHeight += paddingTop + paddingBottom
        setMeasuredDimension(measuredWidth, lineHeight)
    }

    fun setText(text: String, expanded: Boolean, callback: Callback) {
        mText = text
        mExpanded = expanded
        mCallback = callback
        setText(text)
    }

    fun setChanged(expanded: Boolean) {
        mExpanded = expanded
        requestLayout()
    }

    interface Callback {

        fun onExpand()

        fun onCollapse()

        fun onLoss()

    }

}