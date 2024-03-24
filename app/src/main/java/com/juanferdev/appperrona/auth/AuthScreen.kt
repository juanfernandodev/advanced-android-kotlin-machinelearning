package com.juanferdev.appperrona.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juanferdev.appperrona.auth.AuthNavDestinations.LOGIN_SCREEN_DESTINATION
import com.juanferdev.appperrona.auth.AuthNavDestinations.SIGN_UP_SCREEN_DESTINATION

@Composable
fun AuthScreen() {
    val navController = rememberNavController()
    AuthNavHost(navController)
}

@Composable
private fun AuthNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LOGIN_SCREEN_DESTINATION
    ) {
        composable(
            route = LOGIN_SCREEN_DESTINATION
        ) {
            LoginScreen(
                onClickRegister = { navController.navigate(route = SIGN_UP_SCREEN_DESTINATION) }
            )
        }
        composable(
            route = SIGN_UP_SCREEN_DESTINATION
        ) {
            SignUpScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}