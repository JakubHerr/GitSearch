package com.example.gitsearch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gitsearch.data.remote.dto.BranchDto

@Entity(tableName = "branch")
data class Branch(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var repoId: Long = 0L, //links branch to its repository

    var name: String = "",
    var protected: Boolean = false
)

fun Branch.toDto(): BranchDto = BranchDto(name = name, protected = protected)