package com.example.gitsearch.data.repository

import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.util.ErrorMsg
import com.example.gitsearch.data.util.Response
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MainRepositoryImpl(private val api: GithubService) : MainRepository {
    override fun getUser(user: String): Flow<Response<UserDto>> =
        flow {
            emit(Response.Loading())
            try {
                emit(Response.Success(api.getUser(user)))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getUserRepos(user: String): Flow<Response<List<RepoDto>>> =
        flow {
            emit(Response.Loading())

            try {
                emit(Response.Success(api.getRepos(user)))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getRepoBranches(user: String, repo: String): Flow<Response<List<BranchDto>>> =
        flow {
            emit(Response.Loading())

            try {
                emit(Response.Success(api.getBranches(user, repo)))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getRepoCommits(user: String, repo: String): Flow<Response<List<CommitDto>>> =
        flow {
            emit(Response.Loading())

            try {
                emit(Response.Success(api.getCommits(user, repo)))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    private fun getErrorMessage(statusCode: Int): String? = when (statusCode) {
        in 300..399 -> ErrorMsg.Generic3xx.message
        403 -> ErrorMsg.Forbidden.message
        404 -> ErrorMsg.NotFound.message
        418 -> ErrorMsg.Teapot.message
        in 400..499 -> ErrorMsg.Generic4xx.message
        500 -> ErrorMsg.InternalServerError.message
        in 500..599 -> ErrorMsg.Generic5xx.message
        else -> null
    }
}