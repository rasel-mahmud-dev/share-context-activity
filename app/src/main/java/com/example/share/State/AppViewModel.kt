package com.example.share.State

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AppViewModel : ViewModel() {
    var selectedApp by mutableStateOf<List<String>>(emptyList())
        private set

    fun insert(packageName: String) {
        selectedApp = selectedApp + packageName
    }

    fun remove(packageName: String) {
        selectedApp = selectedApp.filter { it != packageName }
    }

    fun isExists(packageName: String): Boolean {
        return selectedApp.contains(packageName)
    }
}
