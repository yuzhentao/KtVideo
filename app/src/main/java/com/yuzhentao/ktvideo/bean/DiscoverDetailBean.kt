package com.yuzhentao.ktvideo.bean

/**
 * http://baobab.kaiyanapp.com/api/v4/categories/detail/tab?id=%s
 */
data class DiscoverDetailBean(
        val categoryInfo: CategoryInfo,
        val tabInfo: TabInfo,
        val tagInfo: Any) {

    data class CategoryInfo(
            val actionUrl: String,
            val dataType: String,
            val description: String,
            val follow: Follow,
            val headerImage: String,
            val id: Int,
            val name: String) {

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
                val tabType: Int)

    }

}




