package com.example.share.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object LocalStorage {
    private const val PREF_NAME = "AppPreferences"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun <T> saveData(context: Context, key: String, data: T) {
        val json = Gson().toJson(data)
        getSharedPreferences(context).edit().putString(key, json).apply()
    }

    fun <T> getData(context: Context, key: String, defaultValue: T): T {
        val json = getSharedPreferences(context).getString(key, null)

        return try {
            if (json == null) {
                defaultValue
            } else {
                val type = object : TypeToken<T>() {}.type
                Gson().fromJson(json, type)
            }
        } catch (e: Exception) {
            Log.e("SharedPreferencesError", "Error parsing JSON for key: $key", e)
            defaultValue
        }



//        return if (json == null) {
//            defaultValue
//        } else {
//            val type = object : TypeToken<T>() {}.type // This can cause the error for generic types.
//            Log.d("type", type.toString())
//            Gson().fromJson(json, type)
//        }

    }
}
