package com.example.apparduino3.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apparduino3.ui.screens.AuthScreen
import com.example.apparduino3.ui.screens.BluetoothScreen
import com.example.apparduino3.ui.screens.HistoryScreen
import com.example.apparduino3.ui.screens.ParkingScreen
import com.example.apparduino3.ui.screens.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "auth") {
        composable("auth") { AuthScreen(navController) }
        composable("signUp") { SignUpScreen(navController) }
        composable("parking") { ParkingScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        composable("bluetooth") { BluetoothScreen(navController) }
    }
}
