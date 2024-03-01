package com.example.pocketstorage.data.repository

import android.util.Log
import com.example.pocketstorage.domain.model.Inventory
import com.example.pocketstorage.domain.model.UserDatabaseRealtime
import com.example.pocketstorage.domain.repository.DatabaseFirebaseRealtimeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.annotation.Inherited
import java.util.UUID
import javax.inject.Inject

class DatabaseFirebaseRealtimeRepositoryImpl @Inject constructor(
    private val databaseRealtime: FirebaseDatabase,
    private val auth: FirebaseAuth) : DatabaseFirebaseRealtimeRepository {
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
            "inventoryItems" to listOf<String>()
            // Другие поля, если необходимо
        )

        currentUserRef?.setValue(userMap)?.addOnSuccessListener {
            Log.d("data", "Данные пользователя успешно сохранены в Realtime Database!")
            createInventoryItemsForUser(userUid ?: "")
        }?.addOnFailureListener { e ->
            Log.e("data", "Ошибка при сохранении данных пользователя: $e")
        }
    }

    private fun createInventoryItemsForUser(userUid: String) {
        val inventoryItemsRef = databaseRealtime.getReference("inventoryItems")

        // Создаем элементы инвентаря для пользователя
        val item1 = Inventory(123, "Item 1", 111111, "Description 1", 1212,3434,"pathImage1")
        val item2 = Inventory(345, "Item 2", 222222, "Description 2", 1212,3434,"pathImage2")

        // Сохраняем элементы инвентаря в базу данных
        inventoryItemsRef.child(item1.id.toString()).setValue(item1)
        inventoryItemsRef.child(item2.id.toString()).setValue(item2)

        // Обновляем список ID инвентарных предметов у пользователя
        val userRef = databaseRealtime.getReference("users").child(userUid)
        userRef.child("inventoryItems").setValue(listOf(item1.id, item2.id))
    }
}