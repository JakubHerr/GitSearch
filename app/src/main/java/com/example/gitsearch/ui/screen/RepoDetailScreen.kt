package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.core.ListItem
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
        when (branchList) {
            is Response.Error -> Text(branchList.message ?: "️Error: no error message")
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> branchList.data?.let { BranchList(list = it) }
        }

        Text(stringResource(R.string.last_ten_commits), style = MaterialTheme.typography.bodyMedium)
        when (commitList) {
            is Response.Error -> Text(branchList.message ?: "️Error: no error message")
            is Response.Loading -> CircularProgressIndicator()
            is Response.Success -> commitList.data?.let { CommitList(list = it) }
        }
    }
}

@Composable
fun BranchList(list: List<BranchDto>) {
    LazyColumn {
        items(list.size) { idx ->
            ListItem(modifier = Modifier) {
                Text(list[idx].name)
            }
        }
    }
}

@Composable
fun CommitList(list: List<CommitDto>) {
    LazyColumn {
        items(list.size) { idx ->
            ListItem(modifier = Modifier) {
                Text(list[idx].commit.message, textAlign = TextAlign.Center)
            }
        }
    }
}
