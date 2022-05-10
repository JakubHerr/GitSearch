package com.example.gitsearch.di

import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.data.remote.GithubServiceImpl
import com.example.gitsearch.data.repository.MainRepository
import com.example.gitsearch.data.repository.MainRepositoryImpl
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
            //responses without 2xx code will throw an exception
            expectSuccess = true
        }
    }
    single<GithubService> {
        GithubServiceImpl(get())
    }
    single<MainRepository> {
        MainRepositoryImpl(get())
    }
}