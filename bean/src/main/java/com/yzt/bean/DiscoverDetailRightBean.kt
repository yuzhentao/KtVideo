package com.yzt.bean

/**
 * 分类详情-广场
 * http://baobab.kaiyanapp.com/api/v6/tag/dynamics?id=16&udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524&deviceModel=SM-G9500
 */
data class DiscoverDetailRightBean(
        val adExist: Boolean,
        val count: Int,
        val itemList: List<Item>,
        val nextPageUrl: String,
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
                val adTrack: Any,
                val content: Content?,
                val dataType: String,
                val header: Header
        ) {

            data class Content(
                    val data: Data?,
                    val adIndex: Int,
                    val id: Int,
                    val tag: Any,
                    val type: String
            ) {

                data class Data(
                        val addWatermark: Boolean,
                        val area: String,
                        val checkStatus: String,
                        val city: String,
                        val collected: Boolean,
                        val consumption: Consumption?,
                        val cover: Cover?,
                        val createTime: Long,
                        val dataType: String,
                        val description: String?,
                        val height: Int,
                        val id: Int,
                        val ifMock: Boolean,
                        val latitude: Double,
                        val library: String,
                        val longitude: Double,
                        val owner: Owner?,
                        val privateMessageActionUrl: String,
                        val recentOnceReply: Any,
                        val releaseTime: Long,
                        val resourceType: String,
                        val selectedTime: Any,
                        val status: String,
                        val tags: List<Tag>?,
                        val title: String?,
                        val uid: Int,
                        val updateTime: Long,
                        val url: String,
                        val urls: List<String>,
                        val urlsWithWatermark: List<String>,
                        val validateResult: String,
                        val validateStatus: String,
                        val width: Int,
                        val playUrl: String?,
                        val duration: Int,
                        var isExpand: Boolean
                ) {//是否扩展

                    data class Tag(
                            val actionUrl: String,
                            val adTrack: Any,
                            val alias: Any,
                            val bgPicture: String,
                            val childTagIdList: Any,
                            val childTagList: Any,
                            val communityIndex: Int,
                            val desc: String,
                            val headerImage: String,
                            val id: Int,
                            val ifShow: Boolean,
                            val level: Int,
                            val name: String?,
                            val parentId: Int,
                            val recWeight: Double,
                            val relationType: Int,
                            val tagRecType: String,
                            val tagStatus: String,
                            val top: Int,
                            val type: String
                    )

                    data class Cover(
                            val blurred: String?,
                            val detail: String,
                            val feed: String?,
                            val homepage: Any,
                            val sharing: Any
                    )

                    data class Consumption(
                            val collectionCount: Int,
                            val playCount: Int,
                            val replyCount: Int,
                            val shareCount: Int
                    )

                    data class Owner(
                            val actionUrl: String,
                            val area: Any,
                            val avatar: String?,
                            val bannedDate: Any,
                            val bannedDays: Any,
                            val birthday: Any,
                            val city: Any,
                            val country: Any,
                            val cover: Any,
                            val description: Any,
                            val expert: Boolean,
                            val followed: Boolean,
                            val gender: Any,
                            val ifPgc: Boolean,
                            val job: Any,
                            val library: String,
                            val limitVideoOpen: Boolean,
                            val nickname: String?,
                            val registDate: Long,
                            val releaseDate: Long,
                            val tagIds: Any,
                            val uid: Int,
                            val university: Any,
                            val uploadStatus: String,
                            val userMedalBeanList: Any,
                            val userType: String,
                            val username: String
                    )

                }

            }

            data class Header(
                    val actionUrl: String,
                    val followType: String,
                    val icon: String,
                    val iconType: String,
                    val id: Int,
                    val issuerName: String,
                    val labelList: Any,
                    val showHateVideo: Boolean,
                    val tagId: Int,
                    val tagName: Any,
                    val time: Long,
                    val topShow: Boolean
            )

        }

    }

}