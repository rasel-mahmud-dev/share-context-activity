package com.example.share

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.share.State.AppViewModel
import com.example.share.screens.AppListScreen
import com.example.share.screens.ChooseAppScreen


@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
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
