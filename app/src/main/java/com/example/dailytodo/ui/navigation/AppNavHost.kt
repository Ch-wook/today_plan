package com.example.dailytodo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dailytodo.ui.alarmsetting.AlarmSettingScreen
import com.example.dailytodo.ui.editor.TodoEditorScreen
import com.example.dailytodo.ui.home.HomeScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TodoEditor : Screen("todo_editor")
    object AlarmSetting : Screen("alarm_setting")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                onAddTodo = { navController.navigate(Screen.TodoEditor.route) },
                onAlarmSettingClick = { navController.navigate(Screen.AlarmSetting.route) }
            )
        }
        composable(Screen.TodoEditor.route) {
            TodoEditorScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.AlarmSetting.route) {
            AlarmSettingScreen(onBack = { navController.popBackStack() })
        }
    }
}
