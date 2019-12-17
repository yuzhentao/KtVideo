package com.yuzhentao.ktvideo.worker

import android.content.Context
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.arialyy.aria.core.Aria
import com.yuzhentao.ktvideo.key.Constant.KT_VIDEO
import com.yuzhentao.ktvideo.ui.activity.SPLASH_URL
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
            Aria.download(this)
                .load(url)
                .setFilePath(Environment.getExternalStorageDirectory().absolutePath + File.separator + KT_VIDEO + File.separator + "splash.png")
                .start()
        }
        return Result.success()
    }

}