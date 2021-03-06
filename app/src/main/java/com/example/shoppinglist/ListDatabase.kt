package com.example.shoppinglist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ItemData::class], version = 1)
@TypeConverters(Converters::class)
abstract class ListDatabase: RoomDatabase() {
    abstract fun getListDao(): ShoppingDao

    companion object{
        @Volatile
        private var instance: ListDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,ListDatabase::class.java,"ListDatabase.db").build()
    }
}