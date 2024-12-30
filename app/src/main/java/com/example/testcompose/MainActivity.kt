package com.example.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            FirstScreen(
                onNavigateToSecondScreen = { navController.navigate("second_screen") },
                viewModel = viewModel
            )
        }
        composable("second_screen") {
            SecondScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}