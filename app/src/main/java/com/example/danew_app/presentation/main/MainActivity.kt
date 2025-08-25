package com.example.danew_app.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.danew.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isLoggedIn = intent.getBooleanExtra("isLoggedIn", false)

        setContent {
            val navHostController = rememberNavController()

            MainScreen(navHostController = navHostController, isLoggedIn = isLoggedIn)

        }
    }
}