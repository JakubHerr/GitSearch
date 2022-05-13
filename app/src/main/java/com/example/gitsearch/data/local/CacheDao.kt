package com.example.gitsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.gitsearch.data.local.entities.Branch
import com.example.gitsearch.data.local.entities.Commit
import com.example.gitsearch.data.local.entities.Repo
import com.example.gitsearch.data.local.entities.User

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBranches(branch: List<Branch>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommits(commit: List<Commit>)
}