package com.example.gitsearch.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

            composable(Screen.Search.route)
            {
                SearchScreen(viewModel) {
                    navController.navigate("${Screen.UserDetail.route}/$it")
                }
            }

            composable(
                route = "${Screen.UserDetail.route}/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    })
            ) {
                UserDetail(painterResource(id = R.drawable.ic_launcher_foreground), onClickRepo = {
                    navController.navigate(Screen.RepoDetail.route) //TODO pass repository name to RepoDetail
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