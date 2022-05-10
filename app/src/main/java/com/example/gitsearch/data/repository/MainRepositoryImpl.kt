package com.example.gitsearch.data.repository

import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.CommitDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.util.Response
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MainRepositoryImpl(private val api: GithubService) : MainRepository {
    override fun getUser(user: String): Flow<Response<UserDto>> = flow {
        emit(Response.Loading())

        try {
            emit(Response.Success(api.getUser(user)))
        } catch (e: ResponseException) {
            emit(Response.Error(getErrorMessage(e.response.status.value)))
        } catch (e: IOException) {
            emit(Response.Error("IOException (No internet?)"))
        } catch (e: Exception) {
            emit(Response.Error("Unspecified exception"))
        }
    }

    override fun getUserRepos(user: String): Flow<Response<List<RepoDto>>> {
        TODO("Not yet implemented")
    }

    override fun getRepoBranches(user: String, repo: String): Flow<Response<List<BranchDto>>> {
        TODO("Not yet implemented")
    }

    override fun getRepoCommits(user: String, repo: String): Flow<Response<List<CommitDto>>> {
        TODO("Not yet implemented")
    }

    private fun getErrorMessage(statusCode: Int): String? = when (statusCode) {
        in 300..399 -> "Redirect response exception"
        404 -> "Not found"
        418 -> "The server is a teapot"
        in 400..499 -> "Client request exception"
        in 500..599 -> "Server response exception"
        else -> null
    }
}