package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.CommitDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class GithubServiceImpl(private val client: HttpClient) : GithubService {
    private val baseUrl = "https://api.github.com"

    //TODO add error handling and response wrapper class
    //return info about a user
    override suspend fun getUser(user: String): UserDto? {
        return client.get { url("$baseUrl/users/$user") }.body()
    }

    //return a list of all repos
    override suspend fun getRepos(user: String): List<RepoDto> {
        TODO("getRepos not implemented")
    }

    //return all branches of a GitHub repository
    override suspend fun getBranches(user: String, repo: String): List<BranchDto> {
        return client.get { url("$baseUrl/repos/$user/$repo/branches") }.body()
    }

    //return all commits of a GitHub repository
    override suspend fun getCommits(user: String, repo: String): List<CommitDto> {
        return client.get { url("$baseUrl + /repos/$user/$repo/commits") }.body()
    }

    //TODO call the close() method to free resources before exiting app
    override fun close() {
        client.close()
    }
}