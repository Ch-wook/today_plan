package com.example.dailytodo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.dailytodo.ui.navigation.AppNavHost
import com.example.dailytodo.ui.theme.DailyTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyTodoTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
