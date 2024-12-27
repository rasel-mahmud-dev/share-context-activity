package com.example.share

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.share.State.AppState
import com.example.share.ui.theme.ShareTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppState.loadData(applicationContext)
        enableEdgeToEdge()
        setContent {
            ShareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding)) {
                        AppNavigation(applicationContext)
                    }
                }
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Add custom actions to the context menu
        menu.add(0, v.id, 0, "Share Link")
        menu.add(0, v.id, 0, "Copy Text")
        menu.add(0, v.id, 0, "My Custom Action")
    }
}

@Composable
fun Greeting(applicationContext: Context) {


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShareTheme {
        Greeting(LocalContext.current)
    }
}