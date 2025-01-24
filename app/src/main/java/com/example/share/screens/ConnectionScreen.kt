package com.example.share.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.share.components.CustomText
import com.example.share.components.PrimaryButton
import com.example.share.database.StateDatabaseHelper
import kotlinx.coroutines.launch


data class Item(
    val fieldName: String,
    val label: String,
    val labelIcon: String,
    val value: String,
    val onChange: () -> Unit,
)


@Composable
fun ConnectionScreen(applicationContext: Context, navHostController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

    val db = StateDatabaseHelper.getInstance(applicationContext)
    val ipAddress = db.getState("ipAddress") ?: ""
    val port = db.getState("port") ?: ""

    var editField by remember { mutableStateOf("") }

    val inputList = listOf(
        Item(
            fieldName = "ipAddress",
            labelIcon = "\uE685",
            label = "Ip Address",
            value = ipAddress,
            onChange = {}),
        Item(
            fieldName = "port",
            labelIcon = "\uE685",
            label = "Port",
            value = port,
            onChange = {}),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF202020), Color(0xFF53308F))
                )
            )
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {


//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp, 0.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight.Medium,
//                        fontFamily = PoppinsFontFamily,
//                        text = "Select your cursor.",
//                        color = Color.White,
//                    )
//
//                    PrimaryButton(label = "Upload Cursor", icon = "\uf0ee", onClick = {})
//
//                }


                inputList.forEach { inputItem ->
                    EditAbleConfig(inputItem, editField, setEditField = {
                        editField = it
                    }, db = db)

                }

//                PrimaryButton(
//                    onClick = {
//                        coroutineScope.launch {
//                        }
//                    },
//                    label = "Save",
//                    icon = "\uf0c7",
//                    px = 80.dp
//                )

            }
        }
    }
}


@Composable
fun EditAbleConfig(
    inputItem: Item,
    editField: String,
    setEditField: (el: String) -> Unit,
    db: StateDatabaseHelper
) {

    var updatedValue by remember { mutableStateOf(inputItem.value) }


    Column(
        modifier = Modifier
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp, 10.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Row(modifier = Modifier.padding(0.dp, 6.dp)) {
                    CustomText(icon = inputItem.labelIcon, pr = 6.dp, fs = 20.sp)
                    CustomText(inputItem.label, Color.White, fs = 20.sp)
                }

                if (editField == inputItem.fieldName) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .weight(1f)
                                .padding(0.dp)
                                .scale(1F, 0.8F),
                            value = updatedValue,
                            onValueChange = { updatedValue = it },
                            shape = RoundedCornerShape(10.dp),
                            placeholder = { Text("Enter your IP address here ex: 192.168.0.148") }
                        )

                        PrimaryButton(
                            onClick = {
                                // Save logic here
                                db.saveState(inputItem.fieldName, updatedValue)
                                setEditField("")
                            },
                            label = "Save",
                            icon = "\uf0c7",
                            px = 10.dp,
                            py = 10.dp,
                            radius = 10.dp
                        )
                    }

                } else {
                    CustomText(updatedValue, fs = 20.sp, pl = 25.dp)
                }
            }

            CustomText(icon = "\uf044", fs = 20.sp, pl = 25.dp,
                onClick = {
                    setEditField(inputItem.fieldName)
                })
        }
    }

}