package com.yzt.common.db

interface DbManager<T> {

    fun insert(data: T)

    fun delete(data: T)

    fun deleteAll()

    fun update(data: T)

    fun find(value: String): T?

    fun findAll(): MutableList<T>?

    fun close()

}