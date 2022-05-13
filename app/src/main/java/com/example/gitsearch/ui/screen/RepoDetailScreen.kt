package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.core.ListItem
import com.example.gitsearch.ui.viewmodel.MainViewModel

@Composable
fun RepoDetailScreen(viewModel: MainViewModel) {
    val branchList by viewModel.branchList.collectAsState()
    val commitList by viewModel.commitList.collectAsState()

    if (branchList is Response.Error || commitList is Response.Error) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = branchList.message ?: stringResource(id = R.string.no_error_message),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (branchList) {
                is Response.Loading -> CircularProgressIndicator()
                is Response.Success -> branchList.data?.let { BranchList(list = it) }
            }

            Spacer(modifier = Modifier.size(8.dp))

            when (commitList) {
                is Response.Loading -> CircularProgressIndicator()
                is Response.Success -> commitList.data?.let { CommitList(list = it) }
            }
        }
    }
}

@Composable
fun BranchList(list: List<BranchDto>) {
    Text(
        text = stringResource(R.string.branches),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    LazyColumn {
        items(list.size) { idx ->
            Text(list[idx].name, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun CommitList(list: List<CommitDto>) {
    Text(
        text = stringResource(R.string.last_ten_commits),
        style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
    )
    LazyColumn {
        items(list.size) { idx ->
            ListItem(modifier = Modifier) {
                Text(list[idx].commit.author.name, fontWeight = FontWeight.Bold)
                Text(list[idx].sha.take(7), color = Color.Blue)
                Spacer(modifier = Modifier.size(4.dp))
                Text(list[idx].commit.message, textAlign = TextAlign.Start)
            }
        }
    }
}
