package com.yuzhentao.ktvideo.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.yuzhentao.ktvideo.R

object ImageUtil {

    fun show(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("Argument Error")
        }
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

    fun showCircle(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("Argument Error")
        }
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
        if (imageView == null) {
            throw IllegalArgumentException("Argument Error")
        }
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