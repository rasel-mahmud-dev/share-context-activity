package com.example.share.screens


import android.R
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.share.State.AppViewModel

@Composable
fun ChooseAppScreen(applicationContext: Context, navController: NavHostController) {
    val packageManager = applicationContext.packageManager

    val appViewModel: AppViewModel = viewModel()

    fun handleAddNew(packageName: String) {
        if (appViewModel.isExists(packageName)) {
            appViewModel.remove(packageName)
        } else {
            appViewModel.insert(packageName)
        }
    }


    var isShowSystemApp by remember { mutableStateOf(false) }

    fun getInstalledApplications(): List<Pair<String, String>> {
        val appList = mutableListOf<Pair<String, String>>()
        val installedApplications =
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (appInfo in installedApplications) {
            val appName = packageManager.getApplicationLabel(appInfo).toString()
            val packageName = appInfo.packageName
            if (isShowSystemApp) {
                appList.add(appName to packageName)
            } else {
                if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                    appList.add(appName to packageName)
                }
            }
        }
        return appList
    }

    val installedApps = getInstalledApplications().sortedBy { it.first.lowercase() }



    Column {

        Column(modifier = Modifier.background(Color(0xFF171717))) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back To Home",
                            tint = Color.White
                        )
                    }
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text = "Select App",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(0.dp, 0.dp, 16.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Switch(
                        checked = isShowSystemApp,
                        onCheckedChange = {
                            isShowSystemApp = it
                        },
                        modifier = Modifier.scale(0.75f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "System",
                        modifier = Modifier,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        LazyColumn {
            items(installedApps) { app ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .padding(0.dp)
                            .clickable {
                                handleAddNew(app.second)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        val iconDrawable: Drawable? = try {
                            packageManager.getApplicationIcon(app.second)
                        } catch (e: Exception) {
                            null
                        }

                        if (iconDrawable != null) {
                            AsyncImage(
                                model = iconDrawable,
                                contentDescription = "App Icon for ${app.first}",
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.sym_def_app_icon),
                                contentDescription = "Fallback App Icon",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = app.first)
                    }

                    Switch(
                        checked = appViewModel.isExists(app.second),
                        onCheckedChange = { handleAddNew(app.second) },
                        modifier = Modifier
                            .scale(0.7f)
                            .padding(0.dp)
                            .height(30.dp)
                    )

                }
            }
        }
    }
}
