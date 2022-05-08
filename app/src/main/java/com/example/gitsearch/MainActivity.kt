package com.example.gitsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitsearch.ui.theme.GitSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitSearchTheme {
                SearchScreen()
            }
        }
    }
}

@Preview
@Composable
fun SearchScreen() {
    val scaffoldState = rememberScaffoldState()
    var textFieldState by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = textFieldState, onValueChange = {
                textFieldState = it
            }, label = {
                Text("Enter a username to search")
            },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(16.dp))
            Button(onClick = {/*TODO add onClick action*/ }) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun UserDetail() {

}

@Composable
fun RepoDetail() {

}

@Composable
fun AboutScreen() {

}