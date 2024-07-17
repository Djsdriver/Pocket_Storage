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

    // Добавлен новый метод для удаления инвентаря по ID
    @Query("DELETE FROM inventory_table WHERE id = :inventoryId")
    suspend fun deleteInventoryById(inventoryId: String)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInventory(inventory: InventoryEntity)

    @Query("UPDATE inventory_table SET name = :newName WHERE id = :inventoryId")
    suspend fun updateInventoryName(inventoryId: String, newName: String)

    @Query("SELECT * FROM inventory_table")
    suspend fun getInventories(): List<InventoryEntity>

    @Query("SELECT * FROM inventory_table WHERE id = :inventoryId")
    suspend fun getInventoryById(inventoryId: String): InventoryEntity

    @Query("SELECT * FROM inventory_table WHERE categoryId = :categoryId")
    suspend fun getInventoriesByCategoryId(categoryId: String): List<InventoryEntity>

    @Query("SELECT * FROM inventory_table WHERE locationId = :locationId")
    suspend fun getInventoriesByLocationId(locationId: String): List<InventoryEntity>
}