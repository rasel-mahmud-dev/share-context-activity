import android.content.pm.PackageManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import android.content.Context
import com.example.share.State.AppViewModel
import com.example.share.components.AppItem

@Composable
fun PackageList(context: Context, appViewModel: AppViewModel) {
    val packageManager = context.packageManager

    val selectedApp = appViewModel.selectedApp

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
            AppItem(app, onPress = {}, packageManager)
        }
    }
}


