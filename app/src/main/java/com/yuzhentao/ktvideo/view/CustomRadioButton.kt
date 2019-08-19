package com.yuzhentao.ktvideo.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.RadioButton
import com.yuzhentao.ktvideo.util.ResourcesUtil

class CustomRadioButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RadioButton(context, attrs, defStyleAttr) {

    private var imgWidth: Float = 0F
    private var imgHeight: Float = 0F

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attrs, com.yuzhentao.ktvideo.R.styleable.CustomRadioButton)
        imgWidth = ta.getDimension(com.yuzhentao.ktvideo.R.styleable.CustomRadioButton_rb_width, ResourcesUtil.getDimensionPixelOffset(context, com.yuzhentao.ktvideo.R.dimen.x20).toFloat())
        imgHeight = ta.getDimension(com.yuzhentao.ktvideo.R.styleable.CustomRadioButton_rb_height, ResourcesUtil.getDimensionPixelOffset(context, com.yuzhentao.ktvideo.R.dimen.x20).toFloat())
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val drawableLeft = this.compoundDrawables[0]//获得文字左侧图片
        val drawableTop = this.compoundDrawables[1]//获得文字顶部图片
        val drawableRight = this.compoundDrawables[2]//获得文字右侧图片
        val drawableBottom = this.compoundDrawables[3]//获得文字底部图片
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, imgWidth.toInt(), imgHeight.toInt())
            this.setCompoundDrawables(drawableLeft, null, null, null)
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, imgWidth.toInt(), imgHeight.toInt())
            this.setCompoundDrawables(null, null, drawableRight, null)
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, imgWidth.toInt(), imgHeight.toInt())
            this.setCompoundDrawables(null, drawableTop, null, null)
        }
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, imgWidth.toInt(), imgHeight.toInt())
            this.setCompoundDrawables(null, null, null, drawableBottom)
        }
    }

}