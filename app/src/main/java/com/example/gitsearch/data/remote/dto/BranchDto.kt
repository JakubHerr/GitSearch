package com.example.gitsearch.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BranchDto(
    val name: String,
    val protected: Boolean
)
