package com.example.gitsearch.data.remote.dto

import com.example.gitsearch.data.local.entities.Branch
import kotlinx.serialization.Serializable

@Serializable
data class BranchDto(
    val name: String,
    val protected: Boolean
)

fun BranchDto.toEntity(repoId: Long): Branch =
    Branch(repoId = repoId, name = name, protected = protected)

fun List<BranchDto>.toEntity(repoId: Long): List<Branch> {
    val result = mutableListOf<Branch>()
    this.forEach { result.add(it.toEntity(repoId)) }
    return result
}