package com.example.share

import android.inputmethodservice.InputMethodService
import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.ComposeView

class CustomKeyboardService : InputMethodService() {

    override fun onCreateInputView(): View {
        Log.d("CustomKeyboardService", "onCreateInputView called")
        // This will create the Compose view that contains the Jetpack Compose layout
        val composeView = ComposeView(this)

        Log.d("CustomKeyboardService", "onCreateInputView called")
        // Rest of the code...

        // Set the content of the ComposeView to the Compose-based layout
        composeView.setContent {
            // Your Compose UI for the keyboard layout
            KeyboardLayout()
        }

        return composeView
    }

    @Composable
    fun KeyboardLayout() {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Example button for 'A'
            Button(onClick = {
                currentInputConnection?.commitText("A", 1)
            }) {
                Text("A")
            }

            Button(onClick = {
                currentInputConnection?.commitText("B", 1)
            }) {
                Text("B")
            }

            // Add more buttons or other components for your keyboard as needed
        }
    }
}