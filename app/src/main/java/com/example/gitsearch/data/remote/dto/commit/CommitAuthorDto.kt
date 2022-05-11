package com.example.gitsearch.data.remote.dto.commit

import kotlinx.serialization.Serializable

@Serializable
data class CommitAuthorDto(
    //default values, because commit author and committer are nullable for some reason
    val name: String = "No name",
    val email: String = "No email",
    val date: String = "1970-01-01T00:00:00Z",
)
