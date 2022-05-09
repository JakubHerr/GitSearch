package com.example.gitsearch

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.gitsearch.data.remote.GitHubApi
import com.example.gitsearch.ui.Navigation
import com.example.gitsearch.ui.theme.GitSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }

        //short demo of GitHubApi
        lifecycleScope.launchWhenCreated {
            GitHubApi.getBranches("JakubHerr", "GitSearch").let { list ->
                list.forEach { branch ->
                    Log.d("API demo", "Retrieved a branch named ${branch.name}")
                }
            }
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






