package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.commit.CommitAuthorDto
import com.example.gitsearch.data.remote.dto.commit.CommitDetailDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto

@Entity(tableName = "commit")
data class Commit(
    @PrimaryKey(autoGenerate = false)
    var sha: String = "",
    var repoId: Long = 0L, //links commit to a repository

    var message: String = "",
    var authorName: String = "No name",
    var authorEmail: String = "No email",
    var date: String = "1970-01-01T00:00:00Z",
)

fun Commit.toDto(): CommitDto {
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

fun List<Commit>.toDto(): List<CommitDto> {
    val result = mutableListOf<CommitDto>()
    this.forEach { result.add(it.toDto()) }
    return result
}