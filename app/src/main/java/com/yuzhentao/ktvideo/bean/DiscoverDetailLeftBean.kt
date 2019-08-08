package com.yuzhentao.ktvideo.bean

/**
 * 分类详情-推荐
 * http://baobab.kaiyanapp.com/api/v1/tag/videos?id=16&udid=49f77cac078741309bee11c5376c233224d8f2d3&vc=524&deviceModel=SM-G9500
 */
data class DiscoverDetailLeftBean(

        val adExist: Boolean,
        val count: Int,
        val itemList: List<Item>,
        val nextPageUrl: String,
        val total: Int) {

    data class Item(
            val data: Data,
            val adIndex: Int,
            val id: Int,
            val tag: Any,
            val type: String) {

        data class Data(
                val adTrack: Any,
                val content: Content,
                val dataType: String,
                val header: Header) {

            data class Content(
                    val data: Data,
                    val adIndex: Int,
                    val id: Int,
                    val tag: Any,
                    val type: String) {

                data class Data(
                        val ad: Boolean,
                        val adTrack: Any,
                        val areaSet: List<Any>,
                        val author: Author,
                        val campaign: Any,
                        val candidateId: Int,
                        val category: String,
                        val collected: Boolean,
                        val consumption: Consumption,
                        val cover: Cover,
                        val createTime: Long,
                        val dataType: String,
                        val date: Long,
                        val description: String,
                        val descriptionEditor: String,
                        val descriptionPgc: Any,
                        val duration: Int,
                        val favoriteAdTrack: Any,
                        val id: Int,
                        val idx: Int,
                        val ifLimitVideo: Boolean,
                        val infoStatus: String,
                        val label: Any,
                        val labelList: List<Any>,
                        val lastViewTime: Any,
                        val library: String,
                        val playInfo: List<PlayInfo>,
                        val playUrl: String,
                        val played: Boolean,
                        val playlists: Any,
                        val premiereDate: Any,
                        val promotion: Any,
                        val provider: Provider,
                        val releaseTime: Long,
                        val remark: Any,
                        val resourceType: String,
                        val searchWeight: Int,
                        val shareAdTrack: Any,
                        val showLabel: Boolean,
                        val slogan: Any,
                        val sourceUrl: String,
                        val src: Any,
                        val status: String,
                        val subtitleStatus: String,
                        val subtitles: List<Any>,
                        val tags: List<Tag>,
                        val tailTimePoint: Int,
                        val thumbPlayUrl: Any,
                        val title: String,
                        val titlePgc: Any,
                        val translateStatus: String,
                        val type: String,
                        val updateTime: Long,
                        val waterMarks: Any,
                        val webAdTrack: Any,
                        val webUrl: WebUrl) {

                    data class Author(
                            val adTrack: Any,
                            val amplifiedLevelId: Int,
                            val approvedNotReadyVideoCount: Int,
                            val authorStatus: String,
                            val authorType: String,
                            val cover: String,
                            val description: String,
                            val expert: Boolean,
                            val follow: Follow,
                            val icon: String,
                            val id: Int,
                            val ifPgc: Boolean,
                            val latestReleaseTime: Long,
                            val library: String,
                            val link: String,
                            val name: String,
                            val pendingVideoCount: Int,
                            val recSort: Int,
                            val shield: Shield,
                            val videoNum: Int) {

                        data class Shield(
                                val itemId: Int,
                                val itemType: String,
                                val shielded: Boolean)

                        data class Follow(
                                val followed: Boolean,
                                val itemId: Int,
                                val itemType: String)

                    }

                    data class Provider(
                            val alias: String,
                            val icon: String,
                            val id: Int,
                            val name: String,
                            val status: String)

                    data class Consumption(
                            val collectionCount: Int,
                            val playCount: Int,
                            val replyCount: Int,
                            val shareCount: Int)

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
                            val name: String,
                            val parentId: Int,
                            val recWeight: Double,
                            val relationType: Int,
                            val tagRecType: String,
                            val tagStatus: String,
                            val top: Int,
                            val type: String)

                    data class Cover(
                            val blurred: String,
                            val detail: String,
                            val feed: String,
                            val homepage: String,
                            val sharing: Any)

                    data class WebUrl(
                            val forWeibo: String,
                            val raw: String)

                    data class PlayInfo(
                            val bitRate: Int,
                            val dimension: String,
                            val duration: Int,
                            val height: Int,
                            val id: Int,
                            val name: String,
                            val size: Int,
                            val type: String,
                            val url: String,
                            val urlList: List<Url>,
                            val videoId: Int,
                            val width: Int) {

                        data class Url(
                                val name: String,
                                val size: Int,
                                val url: String)

                    }

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
                    val topShow: Boolean)

        }

    }

}