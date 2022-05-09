package com.example.gitsearch.ui

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object UserDetail : Screen("userDetail")
    object RepoDetail : Screen("repoDetail")
    object About : Screen("about")
}
