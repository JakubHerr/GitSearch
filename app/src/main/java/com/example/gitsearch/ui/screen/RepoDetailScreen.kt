package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R

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
        Text(stringResource(R.string.last_ten_commits), style = MaterialTheme.typography.h5)
        LazyColumn {

            items(mockCommits.size) {
                Text(text = mockCommits[it])
            }
        }
    }
}