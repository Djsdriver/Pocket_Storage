package com.example.pocketstorage.domain.model

import com.example.pocketstorage.core.utils.UNDEFINED_ID

data class Inventory(
    val id: String = UNDEFINED_ID,
    val name: String = "",
    val description: String = " ",
    val locationId: String = "",
    val categoryId: String = "",
    val pathToImage: String? = ""
){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
