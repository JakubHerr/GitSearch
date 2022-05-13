package com.example.gitsearch.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitsearch.R
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.Response
import com.example.gitsearch.ui.core.ListItem
import com.example.gitsearch.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.datetime.toInstant
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserDetailScreen(viewModel: MainViewModel, onClickRepo: (String, String, Long) -> Unit) {
    val userResponse by viewModel.user.collectAsState()
    val repoListResponse by viewModel.repoList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        when (userResponse) {
            is Response.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                )
            }
            is Response.Error -> {
                userResponse.message?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            is Response.Success -> {
                userResponse.data?.let {
                    UserProfile(user = it)
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(Color.Black)
                            .padding(8.dp)
                    )
                    RepoList(
                        response = repoListResponse,
                        username = it.login,
                        onClickRepo = onClickRepo
                    )
                }
            }
        }
    }
}

@Composable
fun UserProfile(user: UserDto) {
    var showMore by remember { mutableStateOf(false) }

    val hasExtraInfo = when {
        user.bio != null -> true
        user.company != null -> true
        user.location != null -> true
        user.email != null -> true
        else -> false
    }

        Column(modifier = Modifier.animateContentSize()) {
            BasicUserInfo(user = user)
            if (showMore) {
                ExtraUserInfo(user = user, modifier = Modifier.padding(0.dp, 8.dp))
            }
            if (hasExtraInfo) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { showMore = !showMore }) {
                        Text(
                            if (showMore) stringResource(R.string.show_less)
                            else stringResource(R.string.show_more)
                        )
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
                text = stringResource(R.string.followers, user.followers),
                contentDescription = stringResource(R.string.follower_icon_description),
                painter = painterResource(id = R.drawable.ic_baseline_people_alt_24)
            )
            IconText(
                text = stringResource(R.string.following, user.following),
                contentDescription = stringResource(R.string.following_icon_description),
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
                contentDescription = stringResource(R.string.company_icon_description),
                painter = painterResource(id = R.drawable.ic_baseline_location_city_24),
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
        user.location?.let {
            IconText(
                text = it,
                contentDescription = stringResource(R.string.location_icon_description),
                painter = painterResource(id = R.drawable.ic_baseline_location_on_24),
                modifier = Modifier.padding(0.dp, 8.dp)
            )
        }
        user.email?.let {
            IconText(
                text = it,
                contentDescription = stringResource(R.string.email_icon_description),
                painter = painterResource(id = R.drawable.ic_baseline_email_24),
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

@Composable
fun UserAvatar(url: String) {
    GlideImage(
        imageModel = url,
        modifier = Modifier
            .size(128.dp)
            .clip(CircleShape)
            .background(Color.DarkGray),
        contentScale = ContentScale.Crop,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_wifi_off_24),
                contentDescription = stringResource(R.string.error_image_description),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    )
}

@Composable
fun RepoList(
    response: Response<List<RepoDto>>,
    username: String,
    onClickRepo: (String, String, Long) -> Unit
) {
    val test = rememberLazyListState()
    Text(stringResource(R.string.repositories))
    when (response) {
        is Response.Success -> Row {
            LazyColumn(state = test) {

                response.data?.let { list ->
                    items(list.size) { idx ->
                        RepoItem(list[idx], username, onClickRepo)
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
        is Response.Error -> Text(
            text = response.message ?: stringResource(R.string.no_error_message)
        )
    }
    response.data?.let { list ->
        if (list.isEmpty()) Text(text = stringResource(R.string.no_repositories_message))
    }
}

@Composable
fun RepoItem(repo: RepoDto, username: String, onClick: (String, String, Long) -> Unit) {
    ListItem(modifier = Modifier
        .wrapContentHeight()
        .clickable { onClick(username, repo.name, repo.id) }) {
        Text(
            text = repo.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        val date = SimpleDateFormat("MMM dd yyyy", Locale.US)
            .format(repo.updatedAt.toInstant().toEpochMilliseconds())

        Text(
            text = stringResource(R.string.updated_on, date),
            style = MaterialTheme.typography.bodySmall
        )

        repo.language?.let { language ->
            when (language) {
                "Kotlin" -> Text(text = "$language \uD83D\uDC9C")
                else -> Text(text = language)
            }
        }
    }
}