package com.yuzhentao.ktvideo.bean

/**
 * 排行
 * http://baobab.kaiyanapp.com/api/v4/rankList?udid=474a4740c9784203a739fb84a35d51425b59d609&vc=524&deviceModel=SM-G9500
 */
data class RankingBean(
        val tabInfo: TabInfo) {

    data class TabInfo(
            val defaultIdx: Int,
            val tabList: List<Tab>?) {

        data class Tab(
                val adTrack: Any,
                val apiUrl: String?,
                val id: Int,
                val name: String?,
                val nameType: Int,
                val tabType: Int)

    }

}