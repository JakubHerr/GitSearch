package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.viewmodel.MainViewModel

//TODO refactor and show error messages on invalid input/unknown user
@Composable
fun SearchScreen(viewModel: MainViewModel, onValidUser: (String) -> Unit) {
    val userState by viewModel.user.collectAsState()
    val navigate by viewModel.validUser

    if (navigate) LaunchedEffect(Unit) {
        viewModel.onNavigateToUser()
        onValidUser(userState.data!!.login)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (userState is Response.Loading) {
            CircularProgressIndicator()
        }
        SearchBar(viewModel = viewModel)
    }
}

@Composable
fun SearchBar(viewModel: MainViewModel) {
    var textFieldState by rememberSaveable {
        mutableStateOf("")
    }
    var enteredInvalidUsername by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = textFieldState,
        onValueChange = {
            textFieldState = it
            enteredInvalidUsername = false
        },
        label = {
            Text(stringResource(R.string.username_prompt))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = enteredInvalidUsername,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = stringResource(R.string.search_icon_description)
            )
        }
    )
    if (enteredInvalidUsername) {
        Text(
            text = "Invalid username",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
    Button(onClick = {
        if (viewModel.validateUsername(textFieldState)) viewModel.getUser(textFieldState)
        else enteredInvalidUsername = true
    }) {
        Text(stringResource(R.string.search))
    }
}