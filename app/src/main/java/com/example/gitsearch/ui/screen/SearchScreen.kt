package com.example.gitsearch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gitsearch.R
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.viewmodel.MainViewModel

@Composable
fun SearchScreen(viewModel: MainViewModel, onValidUser: (String) -> Unit) {
    var textFieldState by rememberSaveable {
        mutableStateOf("")
    }
    val userState by viewModel.user.collectAsState()
    val navigate by viewModel.validUser
    var enteredInvalidUsername by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (navigate) LaunchedEffect(Unit) {
            viewModel.onNavigateToUser()
            onValidUser(userState.data!!.login)
        }
        if (userState is Response.Loading) {
            CircularProgressIndicator()
        }

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
            isError = enteredInvalidUsername,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_search_24),
                    contentDescription = stringResource(R.string.search_icon_description)
                )
            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            if (viewModel.validateUsername(textFieldState)) viewModel.getUser(textFieldState)
            else enteredInvalidUsername = true
        }) {
            Text(stringResource(R.string.search))
        }
    }
}