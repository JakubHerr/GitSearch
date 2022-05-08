package com.example.gitsearch

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitsearch.ui.theme.GitSearchTheme

private val mockData = listOf("Repo1", "Repo2", "Repo3")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitSearchTheme {
                RepoDetail()
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
            Button(onClick = {/*TODO add onClick action*/ }) {
                Text("Submit")
            }
        }
    }
}

@Preview
@Composable
fun UserDetail() {
    val painter = painterResource(id = R.drawable.ic_launcher_foreground)
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize()) {
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
                        RepoItem(mockData[it])
                    }
                }
            }
        }
    }
}


@Composable
fun RepoItem(text: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
            .clickable {
                Log.d("RepoItem", "$text clicked")
            }, elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text, style = MaterialTheme.typography.h6, color = Color.Blue)
            Text("updated x days ago", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview
@Composable
fun RepoDetail() {
    val mockCommits = listOf("commit1", "commit2", "commit3")
    val mockBranches = listOf("main", "devel", "feature/user-interface")
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState, modifier = Modifier.fillMaxSize()) {
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
}

@Composable
fun AboutScreen() {

}