package com.yzt.ktvideo.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.arialyy.aria.core.Aria
import com.yzt.common.base.App
import com.yzt.ktvideo.ui.activity.SPLASH_URL
import java.io.File

class DownloadSplashWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun doWork(): Result {
        val url = inputData.getString(SPLASH_URL)
        if (!url.isNullOrEmpty()) {
            Aria
                .download(App.app)
                .load(url)
                .setFilePath(context!!.getExternalFilesDir(null)!!.absolutePath + File.separator + "splash.png")
                .create()
        }
        return Result.success()
    }

}