package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.viewmodel.MainViewModel

@Composable
fun RepoDetailScreen(viewModel: MainViewModel, repoName: String?) {
    val branchList by viewModel.branchList.collectAsState()
    val commitList by viewModel.commitList.collectAsState()

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
            Text(repoName ?: "", style = MaterialTheme.typography.h5)
        }
        when (branchList) {
            is Response.Error -> Text(branchList.message ?: "️Error: no error message \uD83E\uDD28")
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> branchList.data?.let { BranchList(list = it) }
        }

        Text(stringResource(R.string.last_ten_commits), style = MaterialTheme.typography.h5)
        when (commitList) {
            is Response.Error -> Text(branchList.message ?: "️Error: no error message \uD83E\uDD28")
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> commitList.data?.let { CommitList(list = it) }
        }
    }
}

@Composable
fun BranchList(list: List<BranchDto>) {
    LazyColumn {
        items(list.size) { idx ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(text = list[idx].name)
            }
        }
    }
}

@Composable
fun CommitList(list: List<CommitDto>) {
    LazyColumn {
        items(list.size) { idx ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {
                Text(text = list[idx].commit.message)
            }
        }
    }
}
