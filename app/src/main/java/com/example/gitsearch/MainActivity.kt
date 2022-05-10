package com.example.gitsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.gitsearch.ui.Navigation
import com.example.gitsearch.ui.theme.GitSearchTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitSearchTheme {
                Navigation()
            }
        }
    }
}






