package com.example.pocketstorage
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.pocketstorage.graphs.RootNavigationGraph
import com.example.pocketstorage.ui.theme.PocketStorageTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //val buildingsList = mutableListOf<Map<String, Any>>()
        val building1: MutableMap<String, Any> = HashMap()
        building1["id"] = "abc123"
        building1["name"] = "Школа Достоевского"
        building1["address"] = "Краснодарская 9"
        val building2: MutableMap<String, Any> = HashMap()
        building2["id"] = "abc777"
        building2["name"] = "Школа Достоевского"
        building2["address"] = "Краснодонская 2д"
        //buildingsList.add(building1)


        val categoriesList = mutableListOf<Map<String, Any>>()
        val category1: MutableMap<String, Any> = HashMap()
        category1["id"] = "category123"
        category1["buildingId"] = "abc123"
        category1["name"] = "Мониторы"
        val category2: MutableMap<String, Any> = HashMap()
        category2["id"] = "category456"
        category2["buildingId"] = "def456"
        category2["name"] = "Планшеты"
        categoriesList.add(category1)
        categoriesList.add(category2)

        val productsList = mutableListOf<Map<String, Any>>()
        val product1: MutableMap<String, Any> = HashMap()
        product1["id"] = "product123"
        product1["categoryId"] = "cat123"
        product1["name"] = "LG monitor"
        product1["price"] = 10.99
        val product2: MutableMap<String, Any> = HashMap()
        product2["id"] = "product456"
        product2["categoryId"] = "cat456"
        product2["name"] = "Product Y"
        product2["price"] = 19.99
        productsList.add(product1)
        productsList.add(product2)

        /*val userDocRef = db.collection("users").document()
        userDocRef.set(user).addOnSuccessListener {
            for (buildingData in buildingsList) {
                val buildingDocRef = userDocRef.collection("buildings").document(buildingData["id"].toString())
                buildingDocRef.set(buildingData).addOnSuccessListener {
                    val categoryId = buildingData["id"].toString()
                    for (categoryData in categoriesList) {
                        if (categoryData["buildingId"].toString() == categoryId) {
                            val categoryDocRef = buildingDocRef.collection("categories").document(categoryData["id"].toString())
                            categoryDocRef.set(categoryData).addOnSuccessListener {
                                val productId = categoryData["id"].toString()
                                for (productData in productsList) {
                                    if (productData["categoryId"].toString() == productId) {
                                        val productDocRef = categoryDocRef.collection("products").document(productData["id"].toString())
                                        productDocRef.set(productData).addOnSuccessListener {
                                            Log.d("FB", "Product ${productData["id"]} added")
                                        }.addOnFailureListener { e ->
                                            Log.d("FB", "Failed to add product: ${e.localizedMessage}")
                                        }
                                    }
                                }
                            }.addOnFailureListener { e ->
                                Log.d("FB", "Failed to add category: ${e.localizedMessage}")
                            }
                        }
                    }
                }.addOnFailureListener { e ->
                    Log.d("FB", "Failed to add building: ${e.localizedMessage}")
                }
            }
        }.addOnFailureListener { e ->
            Log.d("FB", "Failed to add user: ${e.localizedMessage}")
        }*/
        val database = FirebaseDatabase.getInstance()
        val dataRef = database.getReference("data")

        dataRef.child("users").apply {
            // Для user1
            child("user1").apply {
                child("building1").setValue(building1)
                child("building2").setValue(building2)
                child("building1").child("category1").setValue(category1).apply {
                    child("building1").child("category1").child("product").setValue(product1)
                }
            }

            // Для user2
            child("user2").apply {
                child("building1").setValue(building1)
                child("building1").child("category").setValue(category1)
            }
        }


        setContent {
            PocketStorageTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    RootNavigationGraph(navController = rememberNavController())
                }
            }
        }
    }
}