package com.example.gitsearch.data.local.entities

import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.commit.CommitAuthorDto
import com.example.gitsearch.data.remote.dto.commit.CommitDetailDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto

data class Commit(
    @PrimaryKey(autoGenerate = false)
    val sha: String,
    val repoId: Long, //links commit to a repository

    val message: String,
    val authorName: String = "No name",
    val authorEmail: String = "No email",
    val date: String = "1970-01-01T00:00:00Z",
)

fun Commit.toCommitDto(): CommitDto {
    val author = CommitAuthorDto(name = authorName, email = authorEmail, date = date)
    return CommitDto(
        sha = sha,
        commit = CommitDetailDto(
            message = message,
            author = author,
            committer = author
        )
    )
}