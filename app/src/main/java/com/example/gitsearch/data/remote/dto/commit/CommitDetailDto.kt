package com.example.gitsearch.data.remote.dto.commit

import kotlinx.serialization.Serializable

@Serializable
data class CommitDetailDto(
    val message: String,
    val author: CommitAuthorDto,
    val committer: CommitAuthorDto
)
