package com.yuzhentao.ktvideo.bean

/**
 * http://baobab.kaiyanapp.com/api/v6/tag/index?id=16&vc=524&vn=5.7&deviceModel=SM-G9500
 */
data class DiscoverDetailBean(
        val tabInfo: TabInfo,
        val tagInfo: TagInfo) {

    data class TagInfo(
            val actionUrl: Any,
            val bgPicture: String,
            val childTabList: Any,
            val dataType: String,
            val description: String,
            val follow: Follow,
            val headerImage: String,
            val id: Int,
            val lookCount: Int,
            val name: String,
            val recType: Int,
            val shareLinkUrl: String,
            val tagDynamicCount: Int,
            val tagFollowCount: Int,
            val tagVideoCount: Int) {

        data class Follow(
                val followed: Boolean,
                val itemId: Int,
                val itemType: String)

    }

    data class TabInfo(
            val defaultIdx: Int,
            val tabList: List<Tab>) {

        data class Tab(
                val adTrack: Any,
                val apiUrl: String,
                val id: Int,
                val name: String,
                val nameType: Int,
                val tabType: Int
        )

    }

}







