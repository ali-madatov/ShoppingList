package com.example.shoppinglist

class ListRepository(private val db: ListDatabase) {
    suspend fun insert(item: ItemData) = db.getListDao().insert(item)
    suspend fun update(item: ItemData) = db.getListDao().update(item)
    suspend fun delete(item: ItemData) = db.getListDao().delete(item)


    fun allListItems() = db.getListDao().getAllListItems()
}