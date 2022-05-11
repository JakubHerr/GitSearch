package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BranchDto(
    val name: String,
    //TODO add information about the last commit
    val protected: Boolean
)
