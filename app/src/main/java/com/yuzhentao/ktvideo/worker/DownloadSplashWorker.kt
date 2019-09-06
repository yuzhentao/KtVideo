package com.yuzhentao.ktvideo.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadSplashWorker(context: Context, workerParameters: WorkerParameters/*, url: String?*/) :
    Worker(context, workerParameters) {

    private var context: Context? = null
    private var url: String? = null

    init {
        this.context = context
//        this.url = url
    }

    override fun doWork(): Result {
//        url?.let {
//            Aria.download(this)
//                .load(url!!)
//                .setFilePath(Environment.getExternalStorageDirectory().absolutePath + File.separator + "KtVideo")
//                .start()
//        }
        return Result.success()
    }

}