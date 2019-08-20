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

}