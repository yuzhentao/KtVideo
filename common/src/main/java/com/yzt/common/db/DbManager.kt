package com.yzt.common.db

interface DbManager<T> {

    fun insert(data: T)

    fun delete(data: T)

    fun update(data: T)

    fun close()

    fun find(value: String): T?

    fun findAll(): MutableList<T>?

}