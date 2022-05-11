package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto

interface GithubService {
    suspend fun getUser(user: String): UserDto

    suspend fun getRepos(user: String): List<RepoDto>

    suspend fun getBranches(user: String, repo: String): List<BranchDto>

    suspend fun getCommits(user: String, repo: String): List<CommitDto>

    fun close()
}