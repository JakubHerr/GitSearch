package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.CommitDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.Response
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*

class GithubServiceImpl(private val client: HttpClient) : GithubService {
    private val baseUrl = "https://api.github.com"

    //TODO try to reuse catch blocks instead of copying
    override suspend fun getUser(user: String): Response<UserDto> {
        return try {
            Response.Success(client.get { url("$baseUrl/users/$user") }.body())
        } catch (e: ClientRequestException) {
            Response.Error("Client side exception")
        } catch (e: ServerResponseException) {
            Response.Error("Server side exception")
        } catch (e: RedirectResponseException) {
            Response.Error("Redirect exception")
        }
    }

    override suspend fun getRepos(user: String): Response<List<RepoDto>> {
        return try {
            Response.Success(client.get { url("$baseUrl/users/$user/repos") }.body())
        } catch (e: ClientRequestException) {
            Response.Error("Client side exception")
        } catch (e: ServerResponseException) {
            Response.Error("Server side exception")
        } catch (e: RedirectResponseException) {
            Response.Error("Redirect exception")
        }
    }

    override suspend fun getBranches(user: String, repo: String): Response<List<BranchDto>> {
        return try {
            Response.Success(client.get { url("$baseUrl/repos/$user/$repo/branches") }.body())
        } catch (e: ClientRequestException) {
            Response.Error("Client side exception")
        } catch (e: ServerResponseException) {
            Response.Error("Server side exception")
        } catch (e: RedirectResponseException) {
            Response.Error("Redirect exception")
        }
    }

    override suspend fun getCommits(user: String, repo: String): Response<List<CommitDto>> {
        return try {
            Response.Success(client.get { url("$baseUrl + /repos/$user/$repo/commits") }.body())
        } catch (e: ClientRequestException) {
            Response.Error("Client side exception")
        } catch (e: ServerResponseException) {
            Response.Error("Server side exception")
        } catch (e: RedirectResponseException) {
            Response.Error("Redirect exception")
        }
    }

    //TODO call the close() method to free resources before exiting app
    override fun close() {
        client.close()
    }
}