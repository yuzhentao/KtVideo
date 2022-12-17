package com.yzt.common.db

import com.yzt.bean.DownloadState
import com.yzt.bean.VideoBean
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
            Timber.e("插入记录>>>>>${data.title}")
        }
    }

    override fun delete(data: VideoBean) {
        Timber.e("删除记录>>>>>${data.title}")
        data.playUrl?.let {
            val bean = find(it)
            realm?.let { itt ->
                itt.beginTransaction()
                bean!!.deleteFromRealm()
                itt.commitTransaction()
            }
        }
    }

    override fun deleteAll() {
        Timber.e("删除全部记录>>>>>")
        realm?.let {
            it.beginTransaction()
            it.deleteAll()
            it.commitTransaction()
        }
    }

    override fun update(data: VideoBean) {
        Timber.e("更新记录>>>>>${data.title}")
        realm?.let {
            it.beginTransaction()
            it.copyToRealmOrUpdate(data)
            it.commitTransaction()
        }
    }

    override fun find(value: String): VideoBean? {
        Timber.e("查找记录>>>>>${value}")
        return if (realm != null) {
            realm!!.where(VideoBean::class.java).equalTo("playUrl", value).findFirst()
        } else {
            null
        }
    }

    override fun findAll(): MutableList<VideoBean>? {
        Timber.e("查找全部记录>>>>>")
        return if (realm != null) {
            val beans: RealmResults<VideoBean> =
                realm!!.where(VideoBean::class.java).findAllAsync().sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

    fun findCache(): MutableList<VideoBean>? {
        Timber.e("查找全部缓存>>>>>")
        return if (realm != null) {
            val beans: RealmResults<VideoBean> = realm!!.where(VideoBean::class.java)
                .notEqualTo("downloadState", DownloadState.NORMAL.name).findAllAsync()
                .sort("time", Sort.DESCENDING)
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

    override fun close() {
        Timber.e("关闭数据库>>>>>")
        realm?.let {
            if (!it.isClosed) {
                it.close()
            }
        }
    }

}