package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R

@Composable
fun AboutScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = stringResource(R.string.gitsearch),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(R.string.gitsearch_description),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}