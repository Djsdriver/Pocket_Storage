package com.example.pocketstorage.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pocketstorage.presentation.ui.screens.auth.AuthorizationScreen
import com.example.pocketstorage.presentation.ui.screens.auth.RegistrationScreen
import com.example.pocketstorage.presentation.ui.screens.auth.viewmodel.RegistrationViewModel


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {

    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            AuthorizationScreen(
                onClick = {
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(AuthScreen.SignUp.route)
                },
                onForgotClick = {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            )
        }

        composable(route = AuthScreen.SignUp.route) {
            val registrationViewModel = hiltViewModel<RegistrationViewModel>()
            RegistrationScreen(
                onSignUpClickDone = {
                    navController.popBackStack(AuthScreen.Login.route, inclusive = false)
                },
                authViewModel = registrationViewModel,
            )
        }

        /*composable(route = AuthScreen.SignUp.route) {
            ScreenContent(name = AuthScreen.SignUp.route) {}
        }
        composable(route = AuthScreen.Forgot.route) {
            ScreenContent(name = AuthScreen.Forgot.route) {}
        }*/
    }
}

sealed class AuthScreen(val route: String) {
    data object Login : AuthScreen(route = "LOGIN")
    data object SignUp : AuthScreen(route = "SIGN_UP")
    data object Forgot : AuthScreen(route = "FORGOT")
}