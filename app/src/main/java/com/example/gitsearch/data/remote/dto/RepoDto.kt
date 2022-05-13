package com.example.gitsearch.data.remote.dto

import com.example.gitsearch.data.local.entities.Repo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDto(
    val id: Long,
    val name: String,
    val description: String?,
    val language: String?,
    @SerialName("pushed_at")
    val pushedAt: String
)


fun RepoDto.toEntity(userId: Long): Repo = Repo(
    id = id,
    userId = userId,
    name = name,
    language = language,
    pushedAt = pushedAt,
    description = description
)

fun List<RepoDto>.toEntity(userId: Long): List<Repo> {
    val result = mutableListOf<Repo>()
    this.forEach { result.add(it.toEntity(userId)) }
    return result
}
