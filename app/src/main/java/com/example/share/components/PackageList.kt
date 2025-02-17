import android.content.pm.PackageManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.share.State.AppState
import com.example.share.components.AppItem

@Composable
fun PackageList(context: Context) {
    val packageManager = context.packageManager

    val selectedApp = AppState.selectedApp

    val appsInfo = selectedApp.mapNotNull { packageName ->
        try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            appName to packageName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    LazyColumn {
        items(appsInfo) { app ->
            AppItem(modifier = Modifier.padding(8.dp), app, onPress = {}, packageManager)
        }
    }
}


