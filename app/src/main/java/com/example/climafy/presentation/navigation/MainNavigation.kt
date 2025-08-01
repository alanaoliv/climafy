package com.example.climafy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climafy.presentation.ui.screen.FavoriteScreen
import com.example.climafy.presentation.ui.screen.SearchScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchScreen(navController = navController)
        }
        composable("favorites") {
            FavoriteScreen()
        }
    }
}
