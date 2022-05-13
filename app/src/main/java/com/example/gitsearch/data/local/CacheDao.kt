package com.example.gitsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gitsearch.data.local.entities.Branch
import com.example.gitsearch.data.local.entities.Commit
import com.example.gitsearch.data.local.entities.Repo
import com.example.gitsearch.data.local.entities.User

@Dao
interface CacheDao {
    //USER CACHING
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE login = :username COLLATE NOCASE")
    suspend fun getUser(username: String): User?

    //REPO CACHING
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<Repo>)

    @Query("SELECT * FROM repo WHERE userId = :userId ORDER BY pushedAt DESC")
    suspend fun getRepos(userId: Long): List<Repo>?

    //BRANCH CACHING
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBranches(branch: List<Branch>)

    @Query("SELECT * FROM branch WHERE repoId = :repoId")
    suspend fun getBranches(repoId: Long): List<Branch>?


    //COMMIT CACHING
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommits(commit: List<Commit>)

    @Query("SELECT * FROM `commit` WHERE repoId = :repoId ORDER BY date DESC")
    suspend fun getCommits(repoId: Long): List<Commit>?


}