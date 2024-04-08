package com.example.pocketstorage.domain.model

import com.example.pocketstorage.core.utils.UNDEFINED_ID

data class Category(
    val id: String = UNDEFINED_ID,
    val name: String,
    val buildingId: String,
)

fun Category.doesMatchSearchQuery(query: String): Boolean {
    val matchingCombinations = listOf(
        name
    )

    return matchingCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}
