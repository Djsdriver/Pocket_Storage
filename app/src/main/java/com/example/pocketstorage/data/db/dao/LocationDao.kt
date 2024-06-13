package com.example.pocketstorage.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pocketstorage.data.db.model.LocationEntity

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Transaction
    @Query("DELETE FROM location_table WHERE id = :locationId")
    suspend fun deleteLocationAndRelatedEntities(locationId: String)

    @Query("SELECT * FROM location_table")
    suspend fun getLocations(): List<LocationEntity>

    @Query("SELECT * FROM location_table WHERE id = :locationId")
    suspend fun getLocationById(locationId: String): LocationEntity

    @Query("SELECT name FROM location_table WHERE id = :locationId")
    suspend fun getLocationNameById(locationId: String): String
}