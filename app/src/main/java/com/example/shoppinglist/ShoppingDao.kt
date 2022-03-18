package com.example.shoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class ShoppingDao :BaseDao<ItemData> {

    @Query("SELECT * FROM LIST_ITEMS")
    abstract fun getAllListItems(): LiveData<List<ItemData>>
}