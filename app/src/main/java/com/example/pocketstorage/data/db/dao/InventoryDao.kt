package com.example.pocketstorage.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pocketstorage.data.db.model.InventoryEntity

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventory(inventory: InventoryEntity)

    @Delete
    suspend fun deleteInventory(inventory: InventoryEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInventory(inventory: InventoryEntity)

    @Query("SELECT * FROM inventory_table")
    suspend fun getInventories(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_table WHERE id = :inventoryId")
    suspend fun getInventoryById(inventoryId: String): InventoryEntity

    @Query("SELECT * FROM inventory_table WHERE categoryId = :categoryId")
    suspend fun getInventoriesByCategoryId(categoryId: String): List<InventoryEntity>

    @Query("SELECT * FROM inventory_table WHERE locationId = :locationId")
    suspend fun getInventoriesByLocationId(locationId: String): List<InventoryEntity>
}