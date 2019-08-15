package com.yuzhentao.ktvideo.bean

/**
 * 闪屏页
 * http://baobab.kaiyanapp.com/api/v2/configs?udid=b841a78bfa0c443f93868a37c831e4e60e065ae7&vc=524
 */
data class SplashBean(val startPage: StartPage?) {

    data class StartPage(
            val actionUrl: String,
            val imageUrl: String?,
            val version: String
    )

}