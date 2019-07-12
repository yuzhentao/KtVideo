package com.yuzhentao.ktvideo.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yuzhentao.ktvideo.R

class ImageLoadUtils {

    companion object {
        fun display(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null) {
                throw IllegalArgumentException("Argument Error")
            }
            GlideApp
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView)
        }

        fun displayHigh(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null) {
                throw IllegalArgumentException("Argument Error")
            }
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.ic_image_loading)
                    .error(R.drawable.ic_empty_picture)
                    .into(imageView)
        }
    }

}