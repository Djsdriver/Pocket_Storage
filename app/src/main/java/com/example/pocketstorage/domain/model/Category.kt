package com.example.pocketstorage.domain.model

import com.example.pocketstorage.core.utils.UNDEFINED_ID

data class Category(
    val id: String = UNDEFINED_ID,
    val name: String
)
