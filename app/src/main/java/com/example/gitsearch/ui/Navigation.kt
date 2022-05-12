package com.example.gitsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    val currentScreen = navController.currentDestination?.route
    val viewModel = getViewModel<MainViewModel>()

    Scaffold(scaffoldState = scaffoldState, topBar = {
        MyAppBar(
            screenName = "test",
            onBackPressed = { navController.navigateUp() },
            onAboutClick = { navController.navigate(Screen.About.route) })
    }) {
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

@Composable
fun MyAppBar(screenName: String?, onBackPressed: () -> Unit, onAboutClick: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary) {
        Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back arrow",
            modifier = Modifier.clickable {
                onBackPressed()
            })
        Text(text = screenName ?: "") //TODO show screen name
        Icon(Icons.Default.Info, "About app",
            modifier = Modifier
                .fillMaxHeight()
                .clickable {
                    onAboutClick()
                })
    }
}