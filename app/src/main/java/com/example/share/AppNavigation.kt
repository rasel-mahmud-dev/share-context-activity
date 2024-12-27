package com.example.share

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.share.screens.AppListScreen
import com.example.share.screens.ChooseAppScreen


@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController() // NavController for navigation
    NavHost(
        navController = navController,
        startDestination = "choose-app"
    ) {
        composable("home") {
            AppListScreen(context, navController)
        }
        composable("choose-app") {
            ChooseAppScreen(context, navController)
        }
        composable("appDetails/{appName}") { backStackEntry ->
            val appName = backStackEntry.arguments?.getString("appName")
//            AppDetailsScreen(appName ?: "Unknown") // Navigate to App Details Screen
        }
    }
}
