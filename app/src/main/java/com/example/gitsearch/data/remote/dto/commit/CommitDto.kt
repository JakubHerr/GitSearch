package com.example.gitsearch.data.remote.dto.commit

import kotlinx.serialization.Serializable

@Serializable
data class CommitDto(
    val sha: String,
    val commit: CommitDetailDto
)
