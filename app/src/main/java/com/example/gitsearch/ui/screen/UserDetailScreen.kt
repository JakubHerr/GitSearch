package com.example.gitsearch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserDetailScreen(viewModel: MainViewModel, onClickRepo: (String) -> Unit) {
    val user by viewModel.user.collectAsState()
    val repos by viewModel.repoList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        UserProfile(response = user)
        Spacer(modifier = Modifier.height(16.dp))
        RepoList(response = repos, onClickRepo = onClickRepo)
    }
}

@Composable
fun UserProfile(response: Response<UserDto>) {
    var showMore by remember { mutableStateOf(false) }
    response.data?.let { user ->
        Column {
            BasicUserInfo(user = user)
            if (showMore) {
                ExtraUserInfo(user = user, modifier = Modifier.padding(0.dp, 8.dp))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { showMore = !showMore }) {
                    Text("Show more")
                }
            }
        }
    }
}

@Composable
fun BasicUserInfo(user: UserDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        UserAvatar(url = user.avatarUrl)
        //PreviewAvatar()
        Column(
            modifier = Modifier
                .padding(8.dp, 0.dp)
                .wrapContentHeight()
        ) {
            if (user.name != null) Text(
                text = user.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = user.login,
                fontStyle = FontStyle.Italic
            )
            IconText(
                text = "${user.followers} followers",
                contentDescription = "follower icon",
                painter = painterResource(id = R.drawable.ic_baseline_people_alt_24)
            )
            IconText(
                text = "${user.following} following",
                contentDescription = "following icon",
                painter = painterResource(id = R.drawable.ic_baseline_emoji_people_24)
            )

        }
    }
}

@Composable
fun ExtraUserInfo(user: UserDto, modifier: Modifier = Modifier) {
    Column(modifier = modifier.wrapContentHeight()) {
        user.bio?.let { Text(text = it) }
        user.company?.let {
            IconText(
                text = it,
                contentDescription = "company icon",
                painter = painterResource(
                    id = R.drawable.ic_baseline_location_city_24
                ),
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
        user.location?.let {
            IconText(
                text = it,
                contentDescription = "location icon",
                painter = painterResource(
                    id = R.drawable.ic_baseline_location_on_24
                ),
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
        user.email?.let {
            IconText(
                text = it, contentDescription = "email icon", painter = painterResource(
                    id = R.drawable.ic_baseline_email_24
                ),
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
    }
}

@Composable
fun IconText(
    text: String,
    contentDescription: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp)
        )
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    val mockUser = UserDto(
        login = "JakubHerr",
        id = 42,
        avatarUrl = "https://avatars.githubusercontent.com/u/25185228?v=4",
        followers = 333,
        following = 7,
        bio = "bio placeholder",
        name = "Jakub Herrmann",
        company = "Linux",
        email = "email@seznam.cz",
        location = "Earth"
    )
    val mockResponse = Response.Success(mockUser)
    UserProfile(response = mockResponse)
}

@Composable
fun UserAvatar(url: String) {
    GlideImage(
        imageModel = url,
        modifier = Modifier
            .width(128.dp)
            .height(128.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .border(3.dp, Color.DarkGray),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        previewPlaceholder = R.drawable.ic_launcher_background,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
                contentDescription = "Error image",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    )
}

@Composable
fun RepoList(response: Response<List<RepoDto>>, onClickRepo: (String) -> Unit) {
    Text(stringResource(R.string.repositories))
    when (response) {
        is Response.Success -> Row {
            LazyColumn {
                response.data?.let { list ->
                    items(list.size) { idx ->
                        RepoItem(list[idx], onClickRepo)
                    }
                }
            }
        }
        is Response.Loading -> Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
        is Response.Error -> Text(text = response.message ?: "Error: no Error message found")
    }
    response.data?.let { list ->
        if (list.isEmpty()) Text(text = "This user does not have any repositories yet")
    }
}

@Composable
fun RepoItem(repo: RepoDto, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(8.dp)
            .clickable {
                onClick(repo.name) //Mock value for demonstration purposes
            }, elevation = 8.dp, backgroundColor = Color.LightGray
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(repo.name, style = MaterialTheme.typography.h6, color = Color.Blue)
            //not a very efficient way to turn an ISO 8601 date string into a different string
            //TODO look for a better solution
            val date = SimpleDateFormat("MMM dd yyyy", Locale.US)
                .format(repo.updatedAt.toInstant().toEpochMilliseconds())
            Text("Updated on $date", style = MaterialTheme.typography.body2)
        }
    }
}