package com.example.apparduino3.ui.screens

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothScreen(navController: NavController) {
    var isBluetoothEnabled by remember { mutableStateOf(BluetoothAdapter.getDefaultAdapter()?.isEnabled == true) }
    var pairedDevices by remember { mutableStateOf(emptySet<BluetoothDevice>()) }

    val getPairedDevices = { pairedDevices = BluetoothAdapter.getDefaultAdapter()?.bondedDevices ?: emptySet() }

    val requestBluetoothPermissions = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.values.all { it }) {
            // Permissions granted, now we can enable bluetooth
            isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
            if (isBluetoothEnabled) getPairedDevices()
        }
    }

    val enableBluetoothLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter()?.isEnabled == true
        if (isBluetoothEnabled) getPairedDevices()
    }
    
    LaunchedEffect(key1 = Unit) {
        if (isBluetoothEnabled) getPairedDevices()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bluetooth Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            ElevatedCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Bluetooth", style = MaterialTheme.typography.titleMedium)
                    Switch(
                        checked = isBluetoothEnabled,
                        onCheckedChange = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                requestBluetoothPermissions.launch(arrayOf(
                                    Manifest.permission.BLUETOOTH_CONNECT,
                                    Manifest.permission.BLUETOOTH_SCAN
                                ))
                            } else {
                                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                enableBluetoothLauncher.launch(enableBtIntent)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Paired Devices", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            if (!isBluetoothEnabled) {
                Text("Enable Bluetooth to see paired devices.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else if (pairedDevices.isEmpty()) {
                Text("No paired devices found.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(pairedDevices.toList()) { device ->
                        DeviceItem(device = device, onClick = { /* TODO */ })
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceItem(device: BluetoothDevice, onClick: (BluetoothDevice) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(device) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bluetooth,
                contentDescription = "Bluetooth Device",
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text(
                    text = device.name ?: "Unknown Device",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BluetoothScreenPreview() {
    BluetoothScreen(rememberNavController())
}
