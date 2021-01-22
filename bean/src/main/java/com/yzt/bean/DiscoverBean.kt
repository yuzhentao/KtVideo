package com.yzt.bean

/**
 * 发现
 * http://baobab.kaiyanapp.com/api/v4/categories/all?udid=474a4740c9784203a739fb84a35d51425b59d609&vc=524&deviceModel=SM-G9500
 */
data class DiscoverBean(
        val adExist: Boolean,
        val count: Int,
        val itemList: List<Item>,
        val nextPageUrl: Any,
        val total: Int
) {

    data class Item(
            val data: Data?,
            val adIndex: Int,
            val id: Int,
            val tag: Any,
            val type: String
    ) {

        data class Data(
                val actionUrl: String,
                val adTrack: Any,
                val dataType: String,
                val description: Any,
                val id: Int,
                val image: String?,
                val shade: Boolean,
                val title: String?
        )

    }

}