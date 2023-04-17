package com.yzt.common.util

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

/**
 * 保存工具
 *
 * @author yzt
 */
object SaveUtil {

    /**
     * 保存图片到沙盒路径
     */
    fun saveImageInSandbox(
        context: Context?,
        folderName: String?,
        fileName: String?,
        bitmap: Bitmap?,
    ) {
        if (
            context == null
            || folderName.isNullOrEmpty()
            || fileName.isNullOrEmpty()
            || bitmap == null
            || bitmap.isRecycled
        )
            return

        try {
            val picturesFolder: File? =
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)//图片沙盒文件夹
            picturesFolder?.let {
                val folder = File(it.absolutePath + File.separator + folderName)
                if (folder.exists()) {
                    val file =
                        File(it.absolutePath + File.separator + folderName + File.separator + fileName)
                    val fos = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
                    fos.flush()
                    fos.close()
                } else if (folder.mkdir()) {//如果该文件夹不存在，则新建
                    val file =
                        File(it.absolutePath + File.separator + folderName + File.separator + fileName)
                    val fos = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
                    fos.flush()
                    fos.close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}