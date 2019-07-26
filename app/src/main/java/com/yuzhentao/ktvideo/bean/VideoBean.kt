package com.yuzhentao.ktvideo.bean

import android.os.Parcel
import android.os.Parcelable
import com.yuzhentao.ktvideo.util.DownloadState
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class VideoBean(
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
        var time: Long = 0L) : Parcelable, RealmModel {

    @PrimaryKey
    var id: String? = UUID.randomUUID().toString()
    var downloadState: String? = DownloadState.NORMAL.name
    var downloadProgress: Int? = 0

    constructor(parcel: Parcel) : this(
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
            parcel.readLong()) {
        id = parcel.readString()
        downloadState = parcel.readString()
        downloadProgress = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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
        parcel.writeString(id)
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

}