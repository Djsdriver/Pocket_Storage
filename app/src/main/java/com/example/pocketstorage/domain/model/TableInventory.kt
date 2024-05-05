package com.example.pocketstorage.domain.model

import com.example.pocketstorage.core.utils.UNDEFINED_ID
import kotlin.reflect.KProperty0

interface HasStringProperties {
    val properties: List<KProperty0<String>>
}

data class TableInventory(
    val id: String = UNDEFINED_ID,
    val name: String = "",
    val description: String = "",
    val categoryName: String = "",
    val locationName: String = "",
    val locationIndex: String = "",
    val locationAddress: String = ""
) : HasStringProperties {
    override val properties = listOf(
        ::id,
        ::name,
        ::description,
        ::categoryName,
        ::locationName,
        ::locationIndex,
        ::locationAddress
    )
}
