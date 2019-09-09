package com.yuzhentao.ktvideo.worker

import android.content.Context
import android.os.Environment
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.arialyy.aria.core.Aria
import com.yuzhentao.ktvideo.util.SPUtils
import java.io.File

class DownloadSplashWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var context: Context? = null

    init {
        this.context = context
    }

    override fun doWork(): Result {
        val url = SPUtils.getInstance(context!!, "KtVideo").getString("splash_url")
        if (url.isNotEmpty()) {
            Aria.download(this)
                .load(url)
                .setFilePath(Environment.getExternalStorageDirectory().absolutePath + File.separator + "KtVideo" + File.separator + "splash.png")
                .start()
        }
        return Result.success()
    }

}