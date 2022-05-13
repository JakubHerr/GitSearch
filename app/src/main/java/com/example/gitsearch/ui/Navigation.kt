package com.example.gitsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gitsearch.R
import com.example.gitsearch.ui.screen.AboutScreen
import com.example.gitsearch.ui.screen.RepoDetailScreen
import com.example.gitsearch.ui.screen.SearchScreen
import com.example.gitsearch.ui.screen.UserDetailScreen
import com.example.gitsearch.ui.viewmodel.MainViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    var currentScreen by rememberSaveable {
        mutableStateOf("Search")
    }
    val viewModel = getViewModel<MainViewModel>()

    Scaffold(topBar = {
        MyAppBar(
            screenName = currentScreen,
            onBackPressed = { navController.navigateUp() },
            onAboutClick = {
                if (currentScreen != "About") navController.navigate(Screen.About.route)
            })
    }) {
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route,
            modifier = Modifier.padding(it)
        ) {

            composable(Screen.Search.route)
            {
                currentScreen = stringResource(R.string.search)
                SearchScreen(viewModel) { username ->
                    navController.navigate("${Screen.UserDetail.route}/$username")

                }
            }

            composable(
                route = "${Screen.UserDetail.route}/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    })
            ) {
                currentScreen = stringResource(R.string.user_detail)
                UserDetailScreen(
                    viewModel = viewModel,
                    onClickRepo = { repoName ->
                        viewModel.onNavigateToRepo(repoName)
                        navController.navigate("${Screen.RepoDetail.route}/$repoName")
                    })
            }

            composable(route = "${Screen.RepoDetail.route}/{repoName}",
                arguments = listOf(
                    navArgument("repoName") {
                        type = NavType.StringType
                    }
                )) { backstack ->
                backstack.arguments?.getString("repoName").let { repoName ->
                    if (repoName != null) {
                        currentScreen = repoName
                        RepoDetailScreen(viewModel = viewModel)
                    }
                }
            }

            composable(Screen.About.route) {
                currentScreen = stringResource(id = R.string.about)
                AboutScreen()
            }
        }
    }
}

//TODO improve
@Composable
fun MyAppBar(screenName: String?, onBackPressed: () -> Unit, onAboutClick: () -> Unit) {
    //manually overriding default TopBar colors for a better look
    val color = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )

    CenterAlignedTopAppBar(title = { Text(screenName ?: "test") }, actions = {
        Icon(Icons.Default.Info, "About app",
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .clickable {
                    onAboutClick()
                }
                .padding(4.dp))
    }, navigationIcon = {
        if (screenName != "Search") {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
                    .clickable {
                        onBackPressed()
                    }
                    .padding(4.dp))
        }
    }, colors = color)
}