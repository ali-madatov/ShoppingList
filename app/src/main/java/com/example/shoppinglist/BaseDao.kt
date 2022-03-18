package com.example.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

interface BaseDao<ItemData> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ItemData)

    @Update
    fun update(item: ItemData)

    @Delete
    fun delete(item: ItemData)



}