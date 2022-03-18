package com.example.shoppinglist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "list_items")
data class ItemData(
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "itemName") var name: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "priceInHUF") var priceInHUF : Int,
    @ColumnInfo(name = "itemStatus") var isStatus :Boolean,
    @ColumnInfo(name = "quantity") var quantity : Int){
                @PrimaryKey(autoGenerate = true) var itemID: Int? = null
}
