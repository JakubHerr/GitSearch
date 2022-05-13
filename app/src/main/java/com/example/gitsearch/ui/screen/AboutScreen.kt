package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.ui.core.AppLogo

@Composable
fun AboutScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.Start)) {
            AppLogo()
            Text(
                text = stringResource(R.string.gitsearch),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(8.dp)
            )
        }
        Row(modifier = Modifier.padding(0.dp, 16.dp)) {
            Text(
                text = stringResource(R.string.gitsearch_description),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(R.string.author))
        Button(onClick = { onClick() }) {
            Text(text = stringResource(R.string.github_shortcut))
        }

    }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 854)
@Composable
fun AboutScreenPreview() {
    AboutScreen {}
}