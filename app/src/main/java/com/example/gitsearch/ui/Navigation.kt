package com.example.gitsearch.ui

import android.util.Log
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
        mutableStateOf("")
    }
    val viewModel = getViewModel<MainViewModel>()

    Scaffold(topBar = {
        MyAppBar(
            screenName = currentScreen,
            onBackPressed = { navController.navigateUp() },
            onAboutClick = {
                navController.navigate(Screen.About.route)
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
                SearchScreen(viewModel) { username, userId ->
                    //start loading repositories
                    Log.d("GitSearch nav", "Navigating to user profile from search")
                    viewModel.onNavigateToUser(username, userId)
                    navController.navigate("${Screen.UserDetail.route}/$username")
                }
            }

            composable(
                route = "${Screen.UserDetail.route}/{username}",
                arguments = listOf(
                    navArgument("username") {
                        type = NavType.StringType
                    }
                )
            ) {
                currentScreen = stringResource(R.string.user_detail)
                UserDetailScreen(
                    viewModel = viewModel,
                    onClickRepo = { username, repoName, repoId ->
                        Log.d("GitSearch nav", "Navigating to repo detail from user detail")
                        //start loading branches and commits, navigate to repo detail
                        viewModel.onNavigateToRepo(username, repoName, repoId)
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
                AboutScreen {
                    Log.d("GitSearch nav", "Navigating to author profile from About screen")
                    viewModel.showAuthorProfile()
                    viewModel.onNavigateToUser("JakubHerr", 25185228)
                    navController.navigate("${Screen.UserDetail.route}/JakubHerr")

                }
            }
        }
    }
}

@Composable
fun MyAppBar(screenName: String?, onBackPressed: () -> Unit, onAboutClick: () -> Unit) {
    //manually overriding default TopBar colors for a better look
    val color = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )

    CenterAlignedTopAppBar(
        title = { Text(screenName ?: stringResource(R.string.no_title)) },
        actions = {
            if (screenName != stringResource(R.string.about))
                Icon(Icons.Default.Info, stringResource(R.string.about_app_icon_description),
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                        .clickable {
                            onAboutClick()
                        }
                        .padding(4.dp))
        },
        navigationIcon = {
            if (screenName != stringResource(R.string.search)) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_arrow_icon_description),
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