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
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        val user: MutableMap<String, Any> = HashMap()
        user["firstName"] = "Alex"
        user["lastName"] = "Dima"

        val buildingsList = mutableListOf<Map<String, Any>>()
        val building1: MutableMap<String, Any> = HashMap()
        building1["id"] = "abc123"
        building1["name"] = "Building A"
        building1["address"] = "123 Street"
        val building2: MutableMap<String, Any> = HashMap()
        building2["id"] = "def456"
        building2["name"] = "Building B"
        building2["address"] = "456 Avenue"
        buildingsList.add(building1)
        buildingsList.add(building2)

        val categoriesList = mutableListOf<Map<String, Any>>()
        val category1: MutableMap<String, Any> = HashMap()
        category1["id"] = "cat123"
        category1["buildingId"] = "abc123"
        category1["name"] = "Category A"
        val category2: MutableMap<String, Any> = HashMap()
        category2["id"] = "cat456"
        category2["buildingId"] = "def456"
        category2["name"] = "Category B"
        categoriesList.add(category1)
        categoriesList.add(category2)

        val productsList = mutableListOf<Map<String, Any>>()
        val product1: MutableMap<String, Any> = HashMap()
        product1["id"] = "prod123"
        product1["categoryId"] = "cat123"
        product1["name"] = "Product X"
        product1["price"] = 10.99
        val product2: MutableMap<String, Any> = HashMap()
        product2["id"] = "prod456"
        product2["categoryId"] = "cat456"
        product2["name"] = "Product Y"
        product2["price"] = 19.99
        productsList.add(product1)
        productsList.add(product2)

        val userDocRef = db.collection("users").document()
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