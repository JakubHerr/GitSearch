package com.example.gitsearch.data.remote.dto.commit

import com.example.gitsearch.data.local.entities.Commit
import kotlinx.serialization.Serializable

@Serializable
data class CommitDto(
    val sha: String,
    val commit: CommitDetailDto
)

fun CommitDto.toEntity(repoId: Long): Commit = Commit(
    sha = sha,
    repoId = repoId,
    message = commit.message,
    authorName = commit.author.name,
    authorEmail = commit.author.email,
    date = commit.author.date
)

fun List<CommitDto>.toEntity(repoId: Long): List<Commit> {
    val result = mutableListOf<Commit>()
    this.forEach { result.add(it.toEntity(repoId)) }
    return result
}
