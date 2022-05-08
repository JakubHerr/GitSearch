package com.example.gitsearch

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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
            UserDetail(onClickRepo = {
                navController.navigate(Screen.RepoDetail.route)
            })
        }
        composable(Screen.RepoDetail.route) {
            RepoDetail()
        }
    }
}