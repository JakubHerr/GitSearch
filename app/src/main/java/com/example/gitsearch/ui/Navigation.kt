package com.example.gitsearch.ui

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gitsearch.R
import com.example.gitsearch.ui.screen.AboutScreen
import com.example.gitsearch.ui.screen.RepoDetail
import com.example.gitsearch.ui.screen.SearchScreen
import com.example.gitsearch.ui.screen.UserDetail
import com.example.gitsearch.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    //quick test of viewModel injection
    val viewModel = getViewModel<MainViewModel>()

    Scaffold(scaffoldState = scaffoldState) {
        NavHost(navController = navController, startDestination = Screen.Search.route) {

            composable(Screen.Search.route) {
                SearchScreen(
                    onSearch = {
                        Log.d("SearchScreen", "Searched for '$it'")
                        viewModel.getUser(it) //TODO validate user input
                        //TODO pass username to UserDetail screen
                        //navController.navigate(Screen.UserDetail.route)
                    })
            }

            composable(Screen.UserDetail.route) {
                UserDetail(painterResource(id = R.drawable.ic_launcher_foreground), onClickRepo = {
                    navController.navigate(Screen.RepoDetail.route)
                })
            }

            composable(Screen.RepoDetail.route) {
                RepoDetail()
            }

            composable(Screen.About.route) {
                AboutScreen()
            }

        }
    }
}