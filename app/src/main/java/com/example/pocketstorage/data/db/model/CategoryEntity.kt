package com.example.pocketstorage.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pocketstorage.core.utils.UNDEFINED_ID
import com.example.pocketstorage.domain.model.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = UNDEFINED_ID,
    val name: String
)

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = this.id,
        name = this.name
    )
}

fun Category.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        name = this.name
    )
}