package com.example.pocketstorage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pocketstorage.data.db.dao.CategoryDao
import com.example.pocketstorage.data.db.dao.InventoryDao
import com.example.pocketstorage.data.db.dao.LocationDao
import com.example.pocketstorage.data.db.model.CategoryEntity
import com.example.pocketstorage.data.db.model.InventoryEntity
import com.example.pocketstorage.data.db.model.LocationEntity

@Database(
    version = 1,
    entities = [InventoryEntity::class, CategoryEntity::class, LocationEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

    abstract fun categoryDao(): CategoryDao

    abstract fun locationDao(): LocationDao
}