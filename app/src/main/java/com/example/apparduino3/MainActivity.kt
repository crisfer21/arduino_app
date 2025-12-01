package com.example.apparduino3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apparduino3.ui.Navigation
import com.example.apparduino3.ui.theme.AppArduino3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppArduino3Theme {
                Navigation()
            }
        }
    }
}
