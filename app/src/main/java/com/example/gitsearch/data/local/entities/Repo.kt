package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.RepoDto

@Entity(tableName = "repo")
data class Repo(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val userId: Long, //links repository to its owner

    val name: String,
    val description: String?,
    val language: String?,
    val updatedAt: String
)

fun Repo.toDto(): RepoDto = RepoDto(
    id = id,
    name = name,
    language = language,
    updatedAt = updatedAt,
    description = description
)