package com.example.climafy.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.climafy.presentation.navigation.MainNavigation
import com.example.climafy.presentation.viewmodel.ThemeViewModel
import com.example.climafy.ui.theme.ClimafyTheme

@Composable
fun ClimafyApp() {
    val themeViewModel: ThemeViewModel = viewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val navController = rememberNavController()

    ClimafyTheme(darkTheme = isDarkTheme) {
        MainNavigation(
            navController = navController,
            themeViewModel = themeViewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClimafyAppPreview() {
    ClimafyApp()
}