package com.example.pocketstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.rememberNavController
import com.example.pocketstorage.graphs.Graph
import com.example.pocketstorage.graphs.RootNavigationGraph
import com.example.pocketstorage.presentation.ui.screens.auth.viewmodel.AuthorizationViewModel
import com.example.pocketstorage.presentation.ui.screens.inventory.Inventory
import com.example.pocketstorage.ui.theme.PocketStorageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocketStorageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = hiltViewModel<AuthorizationViewModel>()
                    val navController = rememberNavController()

                    if (viewModel.getAuth()) {
                        Inventory()
                    } else {
                        RootNavigationGraph(navController = navController)
                    }
                }
            }
        }
    }
}