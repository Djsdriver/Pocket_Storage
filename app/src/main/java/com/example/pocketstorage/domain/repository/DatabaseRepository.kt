package com.example.pocketstorage.domain.repository

import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertInventory(inventory: Inventory)

    suspend fun deleteInventory(inventory: Inventory)

    suspend fun updateInventory(inventory: Inventory)

    suspend fun getInventoryById(inventoryId: String): Inventory

    fun getInventories(): Flow<List<Inventory>>

    fun getInventoriesByCategoryId(categoryId: String): Flow<List<Inventory>>

    fun getInventoriesByLocationId(locationId: String): Flow<List<Inventory>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun getCategoryById(categoryId: String): Category

    fun getCategoriesByBuildingId(buildingId : String): Flow<List<Category>>

    fun getCategories(): Flow<List<Category>>

    suspend fun insertLocation(location: Location)

    suspend fun deleteLocation(location: Location)

    fun getLocations(): Flow<List<Location>>

    suspend fun getLocationById(locationId: String): Location
}