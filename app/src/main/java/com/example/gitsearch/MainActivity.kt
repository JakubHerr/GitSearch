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
import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.ui.Navigation
import com.example.gitsearch.ui.theme.GitSearchTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val api by inject<GithubService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //calling API to validate error catching
        lifecycleScope.launchWhenCreated {
            val response = api.getUser("asdfgcniagoangajkwf")

            response.data?.let {
                Log.d("API test", "2xx response received, data: $it")
            }
            response.message?.let {
                Log.d("API test", it)
            }
        }

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






