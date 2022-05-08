package com.example.gitsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.gitsearch.ui.theme.GitSearchTheme

private val mockData = listOf("Repo1", "Repo2", "Repo3")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    GitSearchTheme {
        Scaffold(scaffoldState = scaffoldState) {
            Navigation(navController = navController)
        }
    }
}

@Composable
fun SearchScreen(onSearch: (String) -> Unit) {
    var textFieldState by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
            },
            label = {
                Text("Enter a username to search")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = "Search icon"
                )
            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { onSearch(textFieldState) }) {
            Text("Search")
        }
    }
}

@Composable
fun UserDetail(onClickRepo: (Long) -> Unit) {
    val painter = painterResource(id = R.drawable.ic_launcher_foreground)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = "User avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(
                        5.dp, Color.Black,
                        CircleShape
                    )
            )
            Column {
                Text(text = "Username", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Repositories:")
        Row {
            LazyColumn {
                items(mockData.size) {
                    RepoItem(mockData[it], onClickRepo)
                }
            }
        }
    }
}


@Composable
fun RepoItem(text: String, onClick: (Long) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
            .clickable {
                onClick(42L) //Mock value for demonstration purposes
            }, elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text, style = MaterialTheme.typography.h6, color = Color.Blue)
            Text("updated x days ago", style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun RepoDetail() {
    val mockCommits = listOf("commit1", "commit2", "commit3")
    val mockBranches = listOf("main", "devel", "feature/user-interface")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text("Repo name placeholder", style = MaterialTheme.typography.h5)
        }
        LazyColumn {
            items(mockBranches.size) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(0.dp, 8.dp), elevation = 8.dp
                ) {
                    Text(text = mockBranches[it])
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Last 10 commits:", style = MaterialTheme.typography.h5)
        LazyColumn {

            items(mockCommits.size) {
                Text(text = mockCommits[it])
            }
        }
    }
}


@Composable
fun AboutScreen() {

}