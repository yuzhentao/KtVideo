package com.yuzhentao.ktvideo.bean

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class VideoBean(var feed: String?, var title: String?, var description: String?,
                var duration: Int?, var playUrl: String?, var category: String?,
                var blurred: String?, var collect: Int?, var share: Int?, var reply: Int?, var time: Long) : Parcelable, Serializable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readValue(Int::class.java.classLoader) as Int?,
            source.readLong()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(feed)
        writeString(title)
        writeString(description)
        writeValue(duration)
        writeString(playUrl)
        writeString(category)
        writeString(blurred)
        writeValue(collect)
        writeValue(share)
        writeValue(reply)
        writeLong(time)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VideoBean> = object : Parcelable.Creator<VideoBean> {
            override fun createFromParcel(source: Parcel): VideoBean = VideoBean(source)
            override fun newArray(size: Int): Array<VideoBean?> = arrayOfNulls(size)
        }
    }

}