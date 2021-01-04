package com.yzt.common.util

import timber.log.Timber

/**
 * @author yzt 2021/1/4
 */
object TimberUtil {

    fun e(msg: String?) {
        Timber.e(msg)
    }

    fun e(tag: String?, msg: String?) {
        if (tag.isNullOrEmpty()) {
            Timber.e(msg)
        } else {
            Timber.tag(tag).e(msg)
        }
    }

    fun e(e: Throwable?, msg: String?) {
        if (e == null) {
            Timber.e(msg)
        } else {
            Timber.e(e, msg)
        }
    }

}