package com.yzt.bean

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class VideoBean(
    @PrimaryKey
    var id: Int? = 0,
    var feed: String? = "",
    var title: String? = "",
    var description: String? = "",
    var duration: Int? = 0,
    var playUrl: String? = "",
    var category: String? = "",
    var blurred: String? = "",
    var collect: Int? = 0,
    var share: Int? = 0,
    var reply: Int? = 0,
    var time: Long = 0L,
    var savePath: String? = ""
) : Parcelable, RealmModel {

    var downloadId: Long? = 0L
    var downloadState: String? = DownloadState.NORMAL.name
    var downloadProgress: Int? = 0

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readLong(),
        parcel.readString()
    ) {
        downloadId = parcel.readValue(Long::class.java.classLoader) as? Long
        downloadState = parcel.readString()
        downloadProgress = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(feed)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeValue(duration)
        parcel.writeString(playUrl)
        parcel.writeString(category)
        parcel.writeString(blurred)
        parcel.writeValue(collect)
        parcel.writeValue(share)
        parcel.writeValue(reply)
        parcel.writeLong(time)
        parcel.writeString(savePath)
        parcel.writeValue(downloadId)
        parcel.writeString(downloadState)
        parcel.writeValue(downloadProgress)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoBean> {
        override fun createFromParcel(parcel: Parcel): VideoBean {
            return VideoBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoBean?> {
            return arrayOfNulls(size)
        }
    }

//    constructor(parcel: Parcel) : this(
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readValue(Int::class.java.classLoader) as? Int,
//        parcel.readLong(),
//        parcel.readString()
//    ) {
//        downloadId = parcel.readLong()
//        downloadState = parcel.readString()
//        downloadProgress = parcel.readValue(Int::class.java.classLoader) as? Int
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeValue(id)
//        parcel.writeString(feed)
//        parcel.writeString(title)
//        parcel.writeString(description)
//        parcel.writeValue(duration)
//        parcel.writeString(playUrl)
//        parcel.writeString(category)
//        parcel.writeString(blurred)
//        parcel.writeValue(collect)
//        parcel.writeValue(share)
//        parcel.writeValue(reply)
//        parcel.writeLong(time)
//        parcel.writeString(savePath)
//        parcel.writeLong(downloadId)
//        parcel.writeString(downloadState)
//        parcel.writeValue(downloadProgress)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<VideoBean> {
//        override fun createFromParcel(parcel: Parcel): VideoBean {
//            return VideoBean(parcel)
//        }
//
//        override fun newArray(size: Int): Array<VideoBean?> {
//            return arrayOfNulls(size)
//        }
//    }

}