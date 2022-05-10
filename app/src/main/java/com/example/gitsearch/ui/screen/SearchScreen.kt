package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.ui.viewmodel.MainViewModel

@Composable
fun SearchScreen(viewModel: MainViewModel) {
    var textFieldState by rememberSaveable {
        mutableStateOf("")
    }
    val userState by viewModel.user.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${userState.data?.id ?: userState.message ?: ""}") //TODO remove debug text
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
        Button(onClick = { viewModel.getUser(textFieldState) }) {
            Text(stringResource(R.string.search))
        }
    }
}