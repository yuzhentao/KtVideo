package com.yuzhentao.ktvideo.bean

/**
 * http://baobab.kaiyanapp.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
 */
data class DiscoverBean(
        val alias: Any,
        val bgColor: String,
        val bgPicture: String,
        val defaultAuthorId: Int,
        val description: String,
        val headerImage: String,
        val id: Int,
        val name: String,
        val tagId: Int
)