package com.example.pocketstorage.data.repository

import android.util.Log
import com.example.pocketstorage.data.db.AppDatabase
import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabaseFirebaseRealtimeRepositoryImpl @Inject constructor(
    private val databaseRealtime: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val appDatabase: AppDatabase,
) : DatabaseFirebaseRealtimeRepository {

    override suspend fun createUserAndLinkDatabase() {
        val usersRef = databaseRealtime.getReference("users")
        val userUid = auth.currentUser?.uid
        val userName = auth.currentUser?.email?.split("@")?.get(0)
        val userMail = auth.currentUser?.email
        val currentUserRef = userUid?.let { usersRef.child(it) }


        val userMap = mapOf(
            "userUid" to userUid,
            "userName" to userName,
            "userMail" to userMail,
        )

        currentUserRef?.setValue(userMap)?.addOnSuccessListener {
            Log.d("data", "User data successfully saved in Realtime Database!")
            // Launch a coroutine to create inventory items for the user
            GlobalScope.launch { createInventoryItemsForUser() }
        }?.addOnFailureListener { e ->
            Log.e("data", "Error saving user data: $e")
        }
    }

    private suspend fun createInventoryItemsForUser() {
        val userUid = auth.currentUser?.uid

        val inventoryItemsRef = databaseRealtime.getReference("users/$userUid/inventoryItems")
        val categoriesRef = databaseRealtime.getReference("users/$userUid/categories")
        val locationsRef = databaseRealtime.getReference("users/$userUid/locations")

        // Save Inventory Items
        appDatabase.inventoryDao().getInventories().forEach { inventoryEntity ->
            val inventoryMap = mapOf(
                "id" to inventoryEntity.id,
                "name" to inventoryEntity.name,
                "description" to inventoryEntity.description,
                "locationId" to inventoryEntity.locationId,
                "categoryId" to inventoryEntity.categoryId,
                "pathToImage" to inventoryEntity.pathToImage
            )
            inventoryItemsRef.child(inventoryEntity.id).setValue(inventoryMap)
        }

        // Save Categories
        appDatabase.categoryDao().getCategories().forEach { categoryEntity ->
            val categoryMap = mapOf(
                "id" to categoryEntity.id,
                "name" to categoryEntity.name,
                "buildingId" to categoryEntity.buildingId
            )
            categoriesRef.child(categoryEntity.id).setValue(categoryMap)
        }

        // Save Locations
        appDatabase.locationDao().getLocations().forEach { locationEntity ->
            val locationMap = mapOf(
                "id" to locationEntity.id,
                "name" to locationEntity.name,
                "index" to locationEntity.index,
                "address" to locationEntity.address,
            )
            locationsRef.child(locationEntity.id).setValue(locationMap)
        }
    }

    //Для FireStore
    /*override suspend fun createUserAndLinkDatabase() {
        val usersCollection = firestore.collection("users")
        val userUid = auth.currentUser?.uid
        val userName = auth.currentUser?.email?.split("@")?.get(0)
        val userMail = auth.currentUser?.email

        val userDoc = usersCollection.document(userUid ?: "")

        // Create a map for the user with embedded inventory, categories, and locations
        val userMap = mapOf(
            "userName" to userName,
            "userMail" to userMail,
            "inventoryItems" to userUid?.let { createInventoryItemsForUser(it) },
            "categories" to userUid?.let { createCategoriesForUser(it) },
            "locations" to userUid?.let { createLocationsForUser(it) }
        )

        userDoc.set(userMap).addOnSuccessListener {
            Log.d("data", "User data successfully saved in Firestore!")
        }.addOnFailureListener { e ->
            Log.e("data", "Error saving user data: $e")
        }
    }

    private suspend fun createInventoryItemsForUser(userUid: String): List<Map<String, Any>> {
        val inventoryItems = mutableListOf<Map<String, Any>>()

        appDatabase.inventoryDao().getInventories().forEach { inventoryEntity ->
            val inventoryMap = mapOf(
                "name" to inventoryEntity.name,
                "description" to inventoryEntity.description,
                "locationId" to inventoryEntity.locationId,
                "categoryId" to inventoryEntity.categoryId,
                "pathToImage" to inventoryEntity.pathToImage
            )

            inventoryItems.add(inventoryMap)
        }

        return inventoryItems
    }

    private suspend fun createCategoriesForUser(userUid: String): List<Map<String, Any>> {
        val categories = mutableListOf<Map<String, Any>>()

        appDatabase.categoryDao().getCategories().forEach { categoryEntity ->
            val categoryMap = mapOf(
                "name" to categoryEntity.name,
                "buildingId" to categoryEntity.buildingId
            )

            categories.add(categoryMap)
        }

        return categories
    }

    private suspend fun createLocationsForUser(userUid: String): List<Map<String, Any>> {
        val locations = mutableListOf<Map<String, Any>>()

        appDatabase.locationDao().getLocations().forEach { locationEntity ->
            val locationMap = mapOf(
                "name" to locationEntity.name,
                "index" to locationEntity.index,
                "address" to locationEntity.address
            )

            locations.add(locationMap)
        }

        return locations
    }*/
}