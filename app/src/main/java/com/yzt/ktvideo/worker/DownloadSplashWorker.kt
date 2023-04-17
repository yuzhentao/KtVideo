package com.yzt.ktvideo.worker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.yzt.common.key.Constants
import com.yzt.common.key.Keys
import com.yzt.common.util.GlideApp
import com.yzt.common.util.SaveUtil

class DownloadSplashWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun doWork(): Result {
        val url = inputData.getString(Keys.SPLASH_URL)
        if (!url.isNullOrEmpty()) {
            GlideApp
                .with(context!!)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?,
                    ) {
                        SaveUtil.saveImageInSandbox(
                            context!!,
                            Constants.SPLASH_IMAGE_FOLDER_NAME,
                            Constants.SPLASH_IMAGE_FILE_NAME,
                            resource
                        )
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }
        return Result.success()
    }

}