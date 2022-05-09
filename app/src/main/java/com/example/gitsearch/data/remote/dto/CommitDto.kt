package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.Serializable

//TODO figure out how to extract nested values
@Serializable
data class CommitDto(
    val sha: String
)
