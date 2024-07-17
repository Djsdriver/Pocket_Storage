package com.example.pocketstorage.data.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.pocketstorage.domain.model.Location

class BuildingWithInventory(
    @Embedded val building: Location,
    @Relation(
        parentColumn = "buildingId",
        entityColumn = "inventoryId",
        associateBy = Junction(InventoryBuildingCrossRef::class)
    )
    val inventories : List<InventoryEntity>
)
