package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R

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
                Text(stringResource(R.string.username_prompt))
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(R.string.search_icon_description)
                )
            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = { onSearch(textFieldState) }) {
            Text(stringResource(R.string.search))
        }
    }
}