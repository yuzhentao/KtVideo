package com.yzt.ktvideo.util

import android.content.Context
import android.graphics.Typeface
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.yzt.ktvideo.R

object FooterUtil {

    fun getFooter(context: Context, color: Int): View {
        val footer = View.inflate(context, R.layout.item_footer, null)
        footer.findViewById<AppCompatTextView>(R.id.tv_footer).typeface =
            Typeface.createFromAsset(context.assets, "fonts/Lobster-1.4.otf")
        footer.findViewById<AppCompatTextView>(R.id.tv_footer).setTextColor(color)
        return footer
    }

}