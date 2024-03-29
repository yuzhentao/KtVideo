package com.yzt.bean

/**
 * 首页
 * http://baobab.kaiyanapp.com/api/v2/feed?&udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524
 */
data class HomeBean(
        val dialog: Any,
        val issueList: List<Issue>?,
        val newestIssueType: String,
        val nextPageUrl: String,
        val nextPublishTime: Long
) {

    data class Issue(
            val count: Int,
            val date: Long,
            val itemList: List<Item>?,
            val publishTime: Long,
            val releaseTime: Long,
            val type: String
    ) {

        data class Item(
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

                data class Provider(
                        val alias: String,
                        val icon: String,
                        val name: String
                )

                data class WebUrl(
                        val forWeibo: String,
                        val raw: String
                )

                data class Consumption(
                        val collectionCount: Int,
                        val replyCount: Int,
                        val shareCount: Int
                )

                data class Author(
                        val adTrack: Any,
                        val approvedNotReadyVideoCount: Int,
                        val description: String,
                        val expert: Boolean,
                        val follow: Follow,
                        val icon: String?,
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

                data class Cover(
                        val blurred: String?,
                        val detail: String,
                        val feed: String?,
                        val homepage: String,
                        val sharing: Any
                )

            }

        }

    }

}