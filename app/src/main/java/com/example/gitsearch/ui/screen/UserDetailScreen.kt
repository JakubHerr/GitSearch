package com.example.gitsearch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitsearch.R

private val mockData = listOf("Repo1", "Repo2", "Repo3")

@Composable
fun UserDetail(painter: Painter, onClickRepo: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.user_avatar),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(
                        5.dp, Color.Black,
                        CircleShape
                    )
            )
            Column {
                Text(text = "Username", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.repositories))
        Row {
            LazyColumn {
                items(mockData.size) {
                    RepoItem(mockData[it], onClickRepo)
                }
            }
        }
    }
}

@Composable
fun RepoItem(text: String, onClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
            .clickable {
                onClick(42L) //Mock value for demonstration purposes
            }, elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text, style = MaterialTheme.typography.h6, color = Color.Blue)
            Text("updated x days ago", style = MaterialTheme.typography.body2)
        }
    }
}