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
import com.example.gitsearch.data.repository.MainRepository
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.Navigation
import com.example.gitsearch.ui.theme.GitSearchTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val repository by inject<MainRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //calling repository from UI layer to quickly test injection, flow and error handling
        lifecycleScope.launchWhenCreated {
            repository.getUser("daisfhiuagfaf").collect {
                if (it is Response.Loading) {
                    Log.d("Repository flow test", "Loading...")
                }
                it.data?.let { user ->
                    Log.d("Repository flow test", "Collected value: ${user.login}")
                }

                it.message?.let { error ->
                    Log.d("Repository flow test", error)
                }
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






