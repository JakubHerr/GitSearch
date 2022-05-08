package com.example.gitsearch

sealed class Screen(val route: String) {
    object Search : Screen("search")
    object UserDetail : Screen("userDetail")
    object RepoDetail : Screen("repoDetail")
}
