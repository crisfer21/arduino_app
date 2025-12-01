package com.example.apparduino3.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
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
fun ParkingScreen(navController: NavController) {
    // Toggling state for demonstration purposes
    var parkingStatus by remember { mutableStateOf("Occupied") }

    val isAvailable = parkingStatus == "Available"

    // Define colors and icons based on state
    val cardColors = if (isAvailable) {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    } else {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    }
    val contentColor = if (isAvailable) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
    val statusIcon = if (isAvailable) {
        Icons.Filled.CheckCircle
    } else {
        Icons.Filled.DirectionsCar
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parking Status") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("auth") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround // Use SpaceAround for better distribution
        ) {
            // Main status card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp) // Increased height
                    .clickable { // Toggle status on click for demo
                        parkingStatus = if (isAvailable) "Occupied" else "Available"
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = cardColors
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = "Status Icon",
                        modifier = Modifier.size(80.dp),
                        tint = contentColor
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = parkingStatus,
                        style = MaterialTheme.typography.displaySmall, // Adjusted style
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                    Text(
                        "Parking Spot 1",
                        style = MaterialTheme.typography.titleMedium,
                        color = contentColor.copy(alpha = 0.7f)
                    )
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton( // Changed to OutlinedButton for secondary actions
                    onClick = { navController.navigate("history") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.History, contentDescription = "History")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("History")
                }
                OutlinedButton( // Changed to OutlinedButton for secondary actions
                    onClick = { navController.navigate("bluetooth") },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Bluetooth, contentDescription = "Bluetooth")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Settings")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingScreenPreview() {
    ParkingScreen(rememberNavController())
}
