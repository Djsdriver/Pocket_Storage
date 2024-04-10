package com.example.pocketstorage.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.example.pocketstorage.data.db.AppDatabase
import com.example.pocketstorage.data.db.model.toCategory
import com.example.pocketstorage.data.db.model.toCategoryEntity
import com.example.pocketstorage.data.db.model.toInventory
import com.example.pocketstorage.data.db.model.toInventoryEntity
import com.example.pocketstorage.data.db.model.toLocation
import com.example.pocketstorage.data.db.model.toLocationEntity
import com.example.pocketstorage.domain.model.Category
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.Location
import com.example.pocketstorage.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class DatabaseRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val context: Context
) : DatabaseRepository {
    override suspend fun insertInventory(inventory: Inventory) {
        appDatabase.inventoryDao().insertInventory(inventory.toInventoryEntity())
    }

    override suspend fun deleteInventory(inventory: Inventory) {
        appDatabase.inventoryDao().deleteInventory(inventory.toInventoryEntity())
    }

    override suspend fun updateInventory(inventory: Inventory) {
        appDatabase.inventoryDao().updateInventory(inventory.toInventoryEntity())
    }

    override suspend fun getInventoryById(inventoryId: String): Inventory {
        val inventoryEntity = appDatabase.inventoryDao().getInventoryById(inventoryId)
        return inventoryEntity.toInventory()
    }

    override fun getInventories(): Flow<List<Inventory>> = flow {
        val inventoryEntityList = appDatabase.inventoryDao().getInventories()
        val inventoryList = inventoryEntityList.map { inventoryEntity -> inventoryEntity.toInventory() }
        emit(inventoryList)
    }

    override fun getInventoriesByCategoryId(categoryId: String): Flow<List<Inventory>> = flow {
        val inventoryEntityList = appDatabase.inventoryDao().getInventoriesByCategoryId(categoryId)
        val inventoryList = inventoryEntityList.map { inventoryEntity -> inventoryEntity.toInventory() }
        emit(inventoryList)
    }

    override fun getInventoriesByLocationId(locationId: String): Flow<List<Inventory>> = flow {
        val inventoryEntityList = appDatabase.inventoryDao().getInventoriesByLocationId(locationId)
        val inventoryList = inventoryEntityList.map { inventoryEntity -> inventoryEntity.toInventory() }
        emit(inventoryList)
    }

    override suspend fun insertCategory(category: Category) {
        appDatabase.categoryDao().insertCategory(category.toCategoryEntity())
    }

    override suspend fun deleteCategory(category: Category) {
        appDatabase.categoryDao().deleteCategory(category.toCategoryEntity())
    }

    override suspend fun getCategoryById(categoryId: String): Category {
        val categoryEntity =  appDatabase.categoryDao().getCategoryById(categoryId)
        return categoryEntity.toCategory()
    }

    override fun getCategoriesByBuildingId(buildingId: String): Flow<List<Category>> = flow {
        val categoriesByBuildingId = appDatabase.categoryDao().getCategoriesByBuildingId(buildingId = buildingId)
        val categoriesList = categoriesByBuildingId.map { listCategoriesByBuildingId->listCategoriesByBuildingId.toCategory() }
        emit(categoriesList)
    }


    override fun getCategories(): Flow<List<Category>> = flow {
        val categoryEntityList = appDatabase.categoryDao().getCategories()
        val categoryList = categoryEntityList.map { categoryEntity -> categoryEntity.toCategory() }
        emit(categoryList)
    }

    override suspend fun insertLocation(location: Location) {
        appDatabase.locationDao().insertLocation(location.toLocationEntity())
    }

    override suspend fun deleteLocation(location: Location) {
        appDatabase.locationDao().deleteLocation(location.toLocationEntity())
    }

    override fun getLocations(): Flow<List<Location>> = flow {
        val locationEntityList = appDatabase.locationDao().getLocations()
        val locationList = locationEntityList.map { locationEntity -> locationEntity.toLocation() }
        emit(locationList)
    }

    override suspend fun getLocationById(locationId: String): Location {
        val locationEntity = appDatabase.locationDao().getLocationById(locationId)
        return locationEntity.toLocation()
    }

    override suspend fun saveImageToPrivateStorage(uri: Uri, nameOfImage: String): String {
        val filePath =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_album")
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, nameOfImage)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        return file.absolutePath
    }

    override suspend fun deleteImageFromStorage(imagePath: String?) {
        TODO("Not yet implemented")
    }
}