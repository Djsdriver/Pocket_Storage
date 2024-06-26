package com.example.pocketstorage.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pocketstorage.core.utils.UNDEFINED_ID
import com.example.pocketstorage.core.utils.getUniqueIdentifier
import com.example.pocketstorage.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey
    val id: String = UNDEFINED_ID,
    val name: String,
    val buildingId: String,
)

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = this.id,
        name = this.name,
        buildingId = this.buildingId
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = getUniqueIdentifier(),
        name = this.name,
        buildingId = this.buildingId
    )
}

fun Category.toCategoryEntityFromExcel(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        name = this.name,
        buildingId = this.buildingId
    )
}