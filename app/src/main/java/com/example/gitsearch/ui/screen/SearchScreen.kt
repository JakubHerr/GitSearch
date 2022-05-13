package com.example.gitsearch.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(0.dp, 16.dp)
        ) {
            if (userState is Response.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = stringResource(R.string.gitsearch_logo_description),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Green)
                        .align(Alignment.CenterHorizontally)
                )
            }
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
            text = stringResource(R.string.invalid_username),
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