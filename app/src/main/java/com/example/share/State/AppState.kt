package com.example.share.State

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppState {
    // Global app state using mutableStateOf
    var selectedApp by mutableStateOf<List<String>>(emptyList())
        private set

    // Insert a new app into the selectedApp list
    fun insert(packageName: String) {
        selectedApp = selectedApp + packageName  // Adding to the list, immutable update
    }

    // Remove an app from the selectedApp list
    fun remove(packageName: String) {
        selectedApp = selectedApp.filter { it != packageName }  // Remove the package
    }

    // Check if an app is selected
    fun isExists(packageName: String): Boolean {
        return selectedApp.contains(packageName)
    }
}
