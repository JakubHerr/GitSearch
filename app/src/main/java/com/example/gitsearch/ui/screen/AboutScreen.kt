package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AboutScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "GitSearch")
        Text(text = "GitSearch is a simple list-detail app written in Kotlin, it uses:")
        Text(text = "Jetpack Compose for UI")
        Text(text = "Ktor for networking")
        Text(text = "Koin for dependency injection")
        Text(text = "")

    }
}