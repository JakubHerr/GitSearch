package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

//all functions must be called from try-catch blocks
class GithubServiceImpl(private val client: HttpClient) : GithubService {
    private val baseUrl = "https://api.github.com"

    override suspend fun getUser(user: String): UserDto {
        return client.get { url("$baseUrl/users/$user") }.body()
    }

    override suspend fun getRepos(user: String): List<RepoDto> {
        return client.get {
            url("$baseUrl/users/$user/repos")
            parameter("sort", "pushed")
            parameter("per_page", "100")
        }.body()
    }

    override suspend fun getBranches(user: String, repo: String): List<BranchDto> {
        return client.get { url("$baseUrl/repos/$user/$repo/branches") }.body()

    }

    override suspend fun getCommits(user: String, repo: String): List<CommitDto> {
        return client.get {
            url("$baseUrl/repos/$user/$repo/commits")
            parameter("per_page", 10)
        }.body()
    }

    override fun close() {
        client.close()
    }
}