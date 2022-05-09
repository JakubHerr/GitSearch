package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?
)
