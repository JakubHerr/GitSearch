package com.example.gitsearch.data.local.entities

import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.BranchDto

data class Branch(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val repoId: Long, //links branch to its repository

    val name: String,
    val protected: Boolean
)

fun Branch.toDto(): BranchDto = BranchDto(name = name, protected = protected)