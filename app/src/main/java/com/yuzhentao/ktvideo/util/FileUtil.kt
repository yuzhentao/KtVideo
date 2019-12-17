package com.yuzhentao.ktvideo.util

import java.io.File

object FileUtil {

    fun isFileExist(filePath: String?): Boolean {
        if (filePath.isNullOrEmpty()) {
            return false
        }

        val file = File(filePath)
        return file.exists() && file.isFile
    }

    fun deleteFile(filePath: String?): Boolean {
        if (filePath.isNullOrEmpty()) {
            return true
        }

        val file = File(filePath)
        if (!file.exists()) {
            return true
        }

        if (file.isFile) {
            return file.delete()
        }

        if (!file.isDirectory) {
            return false
        }

        for (f in file.listFiles()!!) {
            if (f.isFile) {
                f.delete()
            } else if (f.isDirectory) {
                deleteFile(f.absolutePath)
            }
        }
        return file.delete()
    }

}