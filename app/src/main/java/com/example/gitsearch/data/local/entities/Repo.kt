package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.RepoDto

@Entity(tableName = "repo")
data class Repo(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0L,
    var userId: Long = 0L, //links repository to its owner

    var name: String = "",
    var description: String? = "",
    var language: String? = "",
    var pushedAt: String = ""
)

fun Repo.toDto(): RepoDto = RepoDto(
    id = id,
    name = name,
    language = language,
    pushedAt = pushedAt,
    description = description
)

fun List<Repo>.toRepoDto(): List<RepoDto> {
    val result = mutableListOf<RepoDto>()
    this.forEach { result.add(it.toDto()) }
    return result
}