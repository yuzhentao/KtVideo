package com.yuzhentao.ktvideo.db

import com.yuzhentao.ktvideo.bean.VideoBean
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.deleteFromRealm

class VideoDbManager : DbManager<VideoBean> {

    private var realm: Realm? = Realm.getDefaultInstance()

    override fun insert(data: VideoBean) {
        realm?.let {
            realm!!.beginTransaction()
            realm!!.copyToRealm(data)
            realm!!.commitTransaction()
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
            realm!!.where(VideoBean::class.java).equalTo("playUrl", value).findFirst()!!
        } else {
            null
        }
    }

    override fun findAll(cls: Class<VideoBean>): MutableList<VideoBean>? {
        return if (realm != null) {
            val beans: RealmResults<VideoBean> = realm!!.where(cls).findAllAsync()
            return realm!!.copyFromRealm(beans)
        } else {
            null
        }
    }

}