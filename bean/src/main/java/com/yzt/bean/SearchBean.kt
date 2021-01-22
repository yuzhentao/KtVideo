package com.yzt.bean

/**
 * 搜索
 * http://baobab.kaiyanapp.com/api/v3/search?query=汽车&udid=474a4740c9784203a739fb84a35d51425b59d609&vc=524&deviceModel=SM-G9500
 */
data class SearchBean(
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

            data class Header(
                    val actionUrl: String,
                    val cover: Any,
                    val description: String,
                    val font: Any,
                    val icon: String,
                    val iconType: String,
                    val id: Int,
                    val label: Any,
                    val labelList: Any,
                    val rightText: Any,
                    val showHateVideo: Boolean,
                    val subTitle: Any,
                    val subTitleFont: Any,
                    val textAlign: String,
                    val time: Long,
                    val title: String
            )

            data class Content(
                    val data: Data?,
                    val adIndex: Int,
                    val id: Int,
                    val tag: Any,
                    val type: String
            ) {

                data class Data(
                        val ad: Boolean,
                        val adTrack: Any,
                        val author: Author,
                        val campaign: Any,
                        val category: String?,
                        val collected: Boolean,
                        val consumption: Consumption?,
                        val cover: Cover?,
                        val dataType: String,
                        val date: Long,
                        val description: String?,
                        val descriptionEditor: String,
                        val descriptionPgc: String,
                        val duration: Int,
                        val favoriteAdTrack: Any,
                        val id: Int,
                        val idx: Int,
                        val ifLimitVideo: Boolean,
                        val label: Any,
                        val labelList: List<Any>,
                        val lastViewTime: Any,
                        val library: String,
                        val playInfo: List<PlayInfo>,
                        val playUrl: String?,
                        val played: Boolean,
                        val playlists: Any,
                        val promotion: Any,
                        val provider: Provider,
                        val releaseTime: Long,
                        val remark: String,
                        val resourceType: String,
                        val searchWeight: Int,
                        val shareAdTrack: Any,
                        val slogan: Any,
                        val src: Any,
                        val subtitles: List<Any>,
                        val tags: List<Tag>,
                        val thumbPlayUrl: Any,
                        val title: String?,
                        val titlePgc: String,
                        val type: String,
                        val waterMarks: Any,
                        val webAdTrack: Any,
                        val webUrl: WebUrl
                ) {

                    data class Consumption(
                            val collectionCount: Int,
                            val replyCount: Int,
                            val shareCount: Int
                    )

                    data class Cover(
                            val blurred: String,
                            val detail: String,
                            val feed: String?,
                            val homepage: Any,
                            val sharing: Any
                    )

                    data class Provider(
                            val alias: String,
                            val icon: String,
                            val name: String
                    )

                    data class WebUrl(
                            val forWeibo: String,
                            val raw: String
                    )

                    data class Tag(
                            val actionUrl: String,
                            val adTrack: Any,
                            val bgPicture: String,
                            val childTagIdList: Any,
                            val childTagList: Any,
                            val communityIndex: Int,
                            val desc: String,
                            val headerImage: String,
                            val id: Int,
                            val name: String,
                            val tagRecType: String
                    )

                    data class PlayInfo(
                            val height: Int,
                            val name: String,
                            val type: String,
                            val url: String,
                            val urlList: List<Url>,
                            val width: Int
                    ) {

                        data class Url(
                                val name: String,
                                val size: Int,
                                val url: String
                        )

                    }

                    data class Author(
                            val adTrack: Any,
                            val approvedNotReadyVideoCount: Int,
                            val description: String,
                            val expert: Boolean,
                            val follow: Follow,
                            val icon: String,
                            val id: Int,
                            val ifPgc: Boolean,
                            val latestReleaseTime: Long,
                            val link: String,
                            val name: String,
                            val recSort: Int,
                            val shield: Shield,
                            val videoNum: Int
                    ) {

                        data class Shield(
                                val itemId: Int,
                                val itemType: String,
                                val shielded: Boolean
                        )

                        data class Follow(
                                val followed: Boolean,
                                val itemId: Int,
                                val itemType: String
                        )

                    }

                }

            }

        }

    }

}