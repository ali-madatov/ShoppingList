package com.example.shoppinglist

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toCategory(value: String) = enumValueOf<Category>(value)

    @TypeConverter
    fun fromCategory(value: Category) = value.name
}