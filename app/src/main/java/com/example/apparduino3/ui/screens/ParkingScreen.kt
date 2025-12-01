package com.example.apparduino3.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParkingScreen(navController: NavController) {
    var parkingStatus by remember { mutableStateOf("Unknown") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Parking") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Parking Status:", fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(parkingStatus, fontSize = 32.sp)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("history") }) {
                Icon(Icons.Default.History, contentDescription = "History")
                Spacer(modifier = Modifier.height(8.dp))
                Text("View History")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("bluetooth") }) {
                Icon(Icons.Default.Bluetooth, contentDescription = "Bluetooth")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Bluetooth Settings")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { 
                navController.navigate("auth") {
                    popUpTo("auth") {
                        inclusive = true
                    }
                }
            }) {
                Icon(Icons.Default.Logout, contentDescription = "Logout")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Logout")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingScreenPreview() {
    // We pass a dummy NavController for the preview
    ParkingScreen(navController = rememberNavController())
}
