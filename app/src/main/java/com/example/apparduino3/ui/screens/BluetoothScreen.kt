package com.example.apparduino3.ui.screens

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothScreen(navController: NavController) {
    var isBluetoothEnabled by remember { mutableStateOf(false) }
    var pairedDevices by remember { mutableStateOf(emptySet<BluetoothDevice>()) }

    val requestBluetoothPermissions = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.BLUETOOTH_CONNECT] == true && permissions[Manifest.permission.BLUETOOTH_SCAN] == true) {
            // Permissions granted
        }
    }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Enable Bluetooth")
                Switch(
                    checked = isBluetoothEnabled,
                    onCheckedChange = { 
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            requestBluetoothPermissions.launch(arrayOf(
                                Manifest.permission.BLUETOOTH_CONNECT,
                                Manifest.permission.BLUETOOTH_SCAN
                            ))
                        }
                        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        enableBluetoothLauncher.launch(enableBtIntent)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { pairedDevices = BluetoothAdapter.getDefaultAdapter()?.bondedDevices ?: emptySet() }) {
                Text("Show Paired Devices")
            }
            LazyColumn {
                items(pairedDevices.toList()) { device ->
                    Text(
                        text = "${device.name} - ${device.address}",
                        modifier = Modifier.clickable { /* TODO: Implement connection */ }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BluetoothScreenPreview() {
    BluetoothScreen(rememberNavController())
}
