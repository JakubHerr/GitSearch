package com.example.gitsearch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.gitsearch.data.local.entities.Branch
import com.example.gitsearch.data.local.entities.Commit
import com.example.gitsearch.data.local.entities.Repo
import com.example.gitsearch.data.local.entities.User

@Dao
interface CacheDao {
    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertRepo(repo: Repo)

    @Insert
    suspend fun insertBranch(branch: Branch)

    @Insert
    suspend fun insertCommit(commit: Commit)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}