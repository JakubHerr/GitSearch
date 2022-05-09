package com.example.gitsearch.data.remote

import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.UserDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


object GitHubApi {
    //TODO call the close() method to free resources after use
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    //TODO add error handling and response wrapper class
    suspend fun getUser(): UserDto? {
        return client.get { url("https://api.github.com/users/JakubHerr") }.body()
    }

    suspend fun getBranches(): List<BranchDto?> {
        return client.get { url("https://api.github.com/repos/JakubHerr/GitSearch/branches") }
            .body()
    }

}