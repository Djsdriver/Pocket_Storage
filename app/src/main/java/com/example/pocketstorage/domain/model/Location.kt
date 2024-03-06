package com.example.pocketstorage.domain.model

import com.example.pocketstorage.core.utils.UNDEFINED_ID

data class Location(
    val id: Long = UNDEFINED_ID,
    val name: String = "",
    val index: String= "",
    val address: String= "",
    val isSaved: Boolean = false,


){
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            index,
            address
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

