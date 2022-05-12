package com.example.gitsearch.ui

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gitsearch.ui.screen.AboutScreen
import com.example.gitsearch.ui.screen.RepoDetailScreen
import com.example.gitsearch.ui.screen.SearchScreen
import com.example.gitsearch.ui.screen.UserDetailScreen
import com.example.gitsearch.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Navigation() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
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
                UserDetailScreen(
                    viewModel = viewModel,
                    onClickRepo = {
                        viewModel.onNavigateToRepo(it)
                        navController.navigate("${Screen.RepoDetail.route}/$it")
                    })
            }

            composable(route = "${Screen.RepoDetail.route}/{repoName}",
                arguments = listOf(
                    navArgument("repoName") {
                        type = NavType.StringType
                    }
                )) {
                RepoDetailScreen(viewModel = viewModel, it.arguments?.getString("repoName"))
            }

            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}