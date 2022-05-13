package com.example.gitsearch.ui.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.ErrorMsg
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.core.AppLogo
import com.example.gitsearch.ui.viewmodel.MainViewModel

@Composable
fun SearchScreen(viewModel: MainViewModel, onValidUser: (String) -> Unit) {
    val userState by viewModel.user.collectAsState()
    val navigate by viewModel.validUser

    if (navigate) LaunchedEffect(Unit) {
        Log.d("SearchScreen", "Valid user detected, navigating...")
        userState.data?.let { onValidUser(it.login) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .animateContentSize()
    ) {
        StatusIndicator(userState = userState)
        SearchBar(viewModel = viewModel)
    }
}

@Composable
fun StatusIndicator(userState: Response<UserDto>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp, 16.dp)
    ) {
        when (userState) {
            is Response.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                )
            }
            is Response.Error -> {
                userState.message?.let { errorMsg ->
                    ErrorCause(
                        errorMsg = errorMsg,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            else -> AppLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun ErrorCause(errorMsg: String, modifier: Modifier = Modifier) {
    val text = if (errorMsg == ErrorMsg.NotFound.message)
        stringResource(R.string.user_does_not_exist)
    else errorMsg
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        modifier = modifier
    )
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