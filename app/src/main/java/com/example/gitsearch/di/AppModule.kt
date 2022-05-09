package com.example.gitsearch.di

import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.data.remote.GithubServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val AppModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<GithubService> {
        GithubServiceImpl(get())
    }
}