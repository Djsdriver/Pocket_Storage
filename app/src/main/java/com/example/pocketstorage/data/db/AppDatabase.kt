package com.example.pocketstorage.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pocketstorage.data.db.dao.CategoryDao
import com.example.pocketstorage.data.db.dao.InventoryDao
import com.example.pocketstorage.data.db.dao.LocationDao
import com.example.pocketstorage.data.db.model.CategoryEntity
import com.example.pocketstorage.data.db.model.InventoryEntity
import com.example.pocketstorage.data.db.model.LocationEntity

@Database(
    version = 1,
    entities = [InventoryEntity::class, CategoryEntity::class, LocationEntity::class],
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

    abstract fun categoryDao(): CategoryDao

    abstract fun locationDao(): LocationDao

    //Раскомментировать при миграции
    /*companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Пример другой миграции
                database.execSQL("ALTER TABLE inventory_table ADD COLUMN (заменить на поле в модели) TEXT NOT NULL DEFAULT ''")
            }
        }
    }*/


}

