package com.example.climafy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.climafy.presentation.ui.screen.FavoriteScreen
import com.example.climafy.presentation.ui.screen.SearchScreen
import com.example.climafy.presentation.viewmodel.ThemeViewModel

@Composable
fun MainNavigation(
    navController: NavHostController,
    themeViewModel: ThemeViewModel
    ) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchScreen(
                navController = navController,
                themeViewModel = themeViewModel
            )
        }
        composable("favorites") {
            FavoriteScreen(
                themeViewModel = themeViewModel
            )
        }
    }
}
