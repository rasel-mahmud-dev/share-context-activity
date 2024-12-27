package com.example.share.State

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.share.utils.LocalStorage

object AppState {
    var selectedApp by mutableStateOf(
        listOf(
            "com.google.android.keep",
            "mark.via.gp",
            "com.brave.browser",
            "com.android.chrome"
        )
    )
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

    fun loadData(context: Context) {
        val selectedApwp: List<String> =
            LocalStorage.getData(context, "selectedApp", emptyList())
        selectedApp = selectedApwp
    }

    fun saveData(context: Context) {
        LocalStorage.saveData(context, "selectedApp", selectedApp)
    }
}
