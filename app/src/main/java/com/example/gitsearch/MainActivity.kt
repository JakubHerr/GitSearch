package com.example.gitsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.ui.Navigation
import com.example.gitsearch.ui.theme.GitSearchTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val api by inject<GithubService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    GitSearchTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Navigation(navController = navController)
        }
    }
}






