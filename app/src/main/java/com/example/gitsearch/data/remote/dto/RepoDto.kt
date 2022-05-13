package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @SerialName("updated_at")
    val updatedAt: String
)
