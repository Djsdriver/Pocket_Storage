package com.example.pocketstorage.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pocketstorage.data.db.model.CategoryEntity
import com.example.pocketstorage.data.db.model.LocationEntity
import com.example.pocketstorage.domain.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("SELECT * FROM category_table")
    suspend fun getCategories(): List<CategoryEntity>

    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: String): CategoryEntity
    @Query("SELECT * FROM category_table WHERE id = :buildingId")
    suspend fun getCategoriesByBuildingId(buildingId : String): List<CategoryEntity>
}