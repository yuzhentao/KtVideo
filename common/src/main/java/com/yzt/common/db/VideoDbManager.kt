package com.yzt.common.db

import com.yzt.common.bean.VideoBean
import com.yzt.common.util.DownloadState
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.deleteFromRealm
import timber.log.Timber

class VideoDbManager : DbManager<VideoBean> {

    private var realm: Realm? = Realm.getDefaultInstance()

    override fun insert(data: VideoBean) {
        realm?.let {
            it.beginTransaction()
            it.copyToRealm(data)
            it.commitTransaction()
            Timber.tag("记录").e(">>>${data.title}")
        }
    }

    override fun delete(data: VideoBean) {
        data.playUrl?.let {
            val bean = find(it)
            realm?.let { itt ->
                itt.beginTransaction()
                bean!!.deleteFromRealm()
                itt.commitTransaction()
            }
        }
    }

    override fun update(data: VideoBean) {
        realm?.let {
            it.beginTransaction()
            it.copyToRealmOrUpdate(data)
            it.commitTransaction()
        }
    }

    override fun close() {
        realm?.let {
            if (!it.isClosed) {
                it.close()
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
            val beans: RealmResults<VideoBean> =
                realm!!.where(VideoBean::class.java).findAllAsync().sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

    fun findCache(): MutableList<VideoBean>? {
        return if (realm != null) {
            val beans: RealmResults<VideoBean> = realm!!.where(VideoBean::class.java)
                .notEqualTo("downloadState", DownloadState.NORMAL.name).findAllAsync()
                .sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

}