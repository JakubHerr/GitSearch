package com.example.gitsearch.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitsearch.R
import com.example.gitsearch.ui.screen.AboutScreen
import com.example.gitsearch.ui.screen.RepoDetail
import com.example.gitsearch.ui.screen.SearchScreen
import com.example.gitsearch.ui.screen.UserDetail

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Search.route) {

        composable(Screen.Search.route) {
            SearchScreen(
                onSearch = {
                    Log.d("SearchScreen", "Searched for '$it'")
                    //TODO pass username to UserDetail screen
                    navController.navigate(Screen.UserDetail.route)
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