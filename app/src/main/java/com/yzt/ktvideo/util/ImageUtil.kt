package com.yzt.ktvideo.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.yzt.ktvideo.R
import com.yzt.ktvideo.extension.dimensionPixelOffset

object ImageUtil {

    fun show(context: Context, imageView: ImageView?, url: String) {
        requireNotNull(imageView) { "Argument Error" }
        val drawable: Drawable? = when ((Math.random() * 4).toInt()) {
            0 -> ContextCompat.getDrawable(context, R.drawable.bg_red)
            1 -> ContextCompat.getDrawable(context, R.drawable.bg_yellow)
            2 -> ContextCompat.getDrawable(context, R.drawable.bg_blue)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_green)
        }
        GlideApp
            .with(context)
            .load(url)
            .placeholder(drawable)
            .error(R.drawable.ic_error)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    fun showRoundedCorners(context: Context, imageView: ImageView?, url: String, resId: Int) {
        requireNotNull(imageView) { "Argument Error" }
        val drawable: Drawable? = when ((Math.random() * 4).toInt()) {
            0 -> ContextCompat.getDrawable(context, R.drawable.bg_red)
            1 -> ContextCompat.getDrawable(context, R.drawable.bg_yellow)
            2 -> ContextCompat.getDrawable(context, R.drawable.bg_blue)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_green)
        }
        GlideApp
            .with(context)
            .load(url)
            .placeholder(drawable)
            .error(R.drawable.ic_error)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(context.dimensionPixelOffset(resId))))
            .into(imageView)
    }

    fun showCircle(context: Context, imageView: ImageView?, url: String) {
        requireNotNull(imageView) { "Argument Error" }
        val drawable: Drawable? = when ((Math.random() * 4).toInt()) {
            0 -> ContextCompat.getDrawable(context, R.drawable.bg_red)
            1 -> ContextCompat.getDrawable(context, R.drawable.bg_yellow)
            2 -> ContextCompat.getDrawable(context, R.drawable.bg_blue)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_green)
        }
        GlideApp
            .with(context)
            .load(url)
            .placeholder(drawable)
            .error(R.drawable.ic_error)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    fun showHigh(context: Context, imageView: ImageView?, url: String) {
        requireNotNull(imageView) { "Argument Error" }
        val drawable: Drawable? = when ((Math.random() * 4).toInt()) {
            0 -> ContextCompat.getDrawable(context, R.drawable.bg_red)
            1 -> ContextCompat.getDrawable(context, R.drawable.bg_yellow)
            2 -> ContextCompat.getDrawable(context, R.drawable.bg_blue)
            else -> ContextCompat.getDrawable(context, R.drawable.bg_green)
        }
        GlideApp.with(context)
            .asBitmap()
            .load(url)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .placeholder(drawable)
            .error(R.drawable.ic_error)
            .into(imageView)
    }

}