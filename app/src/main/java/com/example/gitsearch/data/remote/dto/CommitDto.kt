package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommitDto(
    val sha: String
)
