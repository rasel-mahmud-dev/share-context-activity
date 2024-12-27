import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ChooseAppList(context: Context) {
    val packageManager = context.packageManager

    fun getInstalledApplications(): List<Pair<String, String>> {
        val packageManager = packageManager
        val appList = mutableListOf<Pair<String, String>>()

        val installedApplications =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in installedApplications) {
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            val packageName = appInfo.packageName
            appList.add(appName to packageName)
        }
        return appList
    }

    val installedApps = getInstalledApplications()

    LazyColumn {
        items(installedApps) { app ->
            AppItem(appName = app.first, packageName = app.second)
        }
    }
}

@Composable
fun AppItem(appName: String, packageName: String) {
    val context = LocalContext.current
    val packageManager = context.packageManager

    val iconDrawable: Drawable = packageManager.getApplicationIcon(packageName)
    val iconPainter: Painter = rememberAsyncImagePainter(iconDrawable)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
//        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = iconPainter,
                contentDescription = "App Icon",
                modifier = Modifier.size(30.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(text = appName)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

