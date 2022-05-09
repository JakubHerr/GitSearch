package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.CommitDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.Response

interface GithubService {
    suspend fun getUser(user: String): Response<UserDto>

    suspend fun getRepos(user: String): Response<List<RepoDto>>

    suspend fun getBranches(user: String, repo: String): Response<List<BranchDto>>

    suspend fun getCommits(user: String, repo: String): Response<List<CommitDto>>

    fun close()
}