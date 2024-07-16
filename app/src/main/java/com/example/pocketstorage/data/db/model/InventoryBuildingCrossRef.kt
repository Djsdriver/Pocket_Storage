package com.example.pocketstorage.data.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["inventoryId", "buildingId"])
class InventoryBuildingCrossRef(
    val inventoryId : String,
    val building : String,
)