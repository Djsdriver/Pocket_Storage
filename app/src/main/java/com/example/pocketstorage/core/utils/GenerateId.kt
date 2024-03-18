package com.example.pocketstorage.core.utils

import java.util.UUID

fun getUniqueIdentifier(): String {
    return UUID.randomUUID().toString()
}