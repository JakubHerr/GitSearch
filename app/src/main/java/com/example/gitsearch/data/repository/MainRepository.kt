package com.example.gitsearch.data.repository

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.util.Response
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUser(user: String): Flow<Response<UserDto>>
    fun getUserRepos(user: String, userId: Long): Flow<Response<List<RepoDto>>>
    fun getRepoBranches(user: String, repo: String, repoId: Long): Flow<Response<List<BranchDto>>>
    fun getRepoCommits(user: String, repo: String, repoId: Long): Flow<Response<List<CommitDto>>>
}