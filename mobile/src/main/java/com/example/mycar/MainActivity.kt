package com.example.mycar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.car.app.connection.CarConnection
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.mycar.ui.theme.MyCarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val carConnection = CarConnection(this)

        setContent {
            MyCarTheme {
                var connectionState by remember { mutableStateOf(CarConnection.CONNECTION_TYPE_NOT_CONNECTED) }
                var showAboutDialog by remember { mutableStateOf(false) }

                DisposableEffect(carConnection) {
                    val observer = Observer<Int> { state ->
                        connectionState = state ?: CarConnection.CONNECTION_TYPE_NOT_CONNECTED
                    }
                    carConnection.type.observe(this@MainActivity, observer)
                    onDispose {
                        carConnection.type.removeObserver(observer)
                    }
                }

                val connectionMessage = when (connectionState) {
                    CarConnection.CONNECTION_TYPE_PROJECTION,
                    CarConnection.CONNECTION_TYPE_NATIVE -> "App is running on car screen"
                    else -> "Android Auto Not Connected"
                }

                val versionName = try {
                    packageManager.getPackageInfo(packageName, 0).versionName ?: "1.0"
                } catch (e: Exception) {
                    "1.0"
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = connectionMessage,
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(onClick = { showAboutDialog = true }) {
                                Text(text = stringResource(id = R.string.menu_about))
                            }
                        }

                        if (showAboutDialog) {
                            AlertDialog(
                                onDismissRequest = { showAboutDialog = false },
                                title = { Text(text = stringResource(id = R.string.about_title)) },
                                text = {
                                    Column {
                                        Text(text = stringResource(id = R.string.about_desc))
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(text = stringResource(id = R.string.app_version, versionName))
                                    }
                                },
                                confirmButton = {
                                    TextButton(onClick = { showAboutDialog = false }) {
                                        Text(text = "OK")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
