package com.yzt.ktvideo.db

import com.yzt.ktvideo.bean.VideoBean
import com.yzt.ktvideo.util.DownloadState
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.deleteFromRealm
import timber.log.Timber

class VideoDbManager : DbManager<VideoBean> {

    private var realm: Realm? = Realm.getDefaultInstance()

    override fun insert(data: VideoBean) {
        realm?.let {
            realm!!.beginTransaction()
            realm!!.copyToRealm(data)
            realm!!.commitTransaction()
            Timber.tag("记录").e(">>>${data.title}")
        }
    }

    override fun delete(data: VideoBean) {
        data.playUrl?.let {
            val bean = find(data.playUrl!!)
            realm?.let {
                realm!!.beginTransaction()
                bean!!.deleteFromRealm()
                realm!!.commitTransaction()
            }
        }
    }

    override fun update(data: VideoBean) {
        realm?.let {
            realm!!.beginTransaction()
            realm!!.copyToRealmOrUpdate(data)
            realm!!.commitTransaction()
        }
    }

    override fun close() {
        realm?.let {
            if (!realm!!.isClosed) {
                realm!!.close()
            }
        }
    }

    override fun find(value: String): VideoBean? {
        return if (realm != null) {
            realm!!.where(VideoBean::class.java).equalTo("playUrl", value).findFirst()
        } else {
            null
        }
    }

    override fun findAll(): MutableList<VideoBean>? {
        return if (realm != null) {
            val beans: RealmResults<VideoBean> = realm!!.where(VideoBean::class.java).findAllAsync().sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

    fun findCache(): MutableList<VideoBean>? {
        return if (realm != null) {
            val beans: RealmResults<VideoBean> = realm!!.where(VideoBean::class.java).notEqualTo("downloadState", DownloadState.NORMAL.name).findAllAsync().sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

}