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
fun PackageList(context: Context) {
    val packageManager = context.packageManager

    val hardcodedPackages = listOf(
        "com.google.android.keep",
        "mark.via.gp",
        "com.brave.browser",
        "com.android.chrome"
    )

    val appsInfo = hardcodedPackages.mapNotNull { packageName ->
        try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            appInfo to packageName // Pair with the app's info and package name
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

    LazyColumn {
        items(appsInfo) { (appInfo, packageName) ->
            AppItem(appInfo = appInfo, packageName = packageName)
        }
    }
}

@Composable
fun AppItem(appInfo: ApplicationInfo, packageName: String) {
    val context = LocalContext.current
    val packageManager = context.packageManager

    val iconDrawable: Drawable = packageManager.getApplicationIcon(appInfo)
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

            // Display the app name
            Text(text = packageManager.getApplicationLabel(appInfo).toString())

            Spacer(modifier = Modifier.weight(1f))

            // Optional: Add a button to launch the app
            Button(onClick = {
                val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
                launchIntent?.let { context.startActivity(it) }
            }, modifier = Modifier.height(35.dp)) {
                Text("Open")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PackageListPreview() {
    PackageList(context = LocalContext.current)
}
