package com.example.share

import android.content.Context

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.share.screens.AboutScreen
import com.example.share.screens.ChooseAppScreen
import com.example.share.screens.ConnectionScreen
import com.example.share.screens.CursorStylesScreen
import com.example.share.screens.HomeScreen


@Composable
fun AppNavigation(context: Context, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "cursor-styles"
    ) {
        composable("home") {
            HomeScreen(context, navController)
        }
        composable("cursor-styles") {
            CursorStylesScreen(context, navController)
        }
        composable("pc-connection") {
            ConnectionScreen(context, navController)
        }
        composable("choose-app") {
            ChooseAppScreen(context, navController)
        }
        composable("about") {
            AboutScreen(context, navController)
        }
        composable("appDetails/{appName}") { backStackEntry ->
            val appName = backStackEntry.arguments?.getString("appName")
//            AppDetailsScreen(appName ?: "Unknown") // Navigate to App Details Screen
        }
    }
}
