package com.example.gitsearch.data.repository

import com.example.gitsearch.data.local.CacheDao
import com.example.gitsearch.data.remote.GithubService
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.remote.dto.commit.toEntity
import com.example.gitsearch.data.remote.dto.toEntity
import com.example.gitsearch.data.util.ErrorMsg
import com.example.gitsearch.data.util.Response
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MainRepositoryImpl(
    private val api: GithubService,
    private val dao: CacheDao
) : MainRepository {
    override fun getUser(user: String): Flow<Response<UserDto>> =
        flow {
            emit(Response.Loading())
            try {
                val response = api.getUser(user)
                emit(Response.Success(response))
                dao.insertUser(response.toEntity())
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getUserRepos(user: String, userId: Long): Flow<Response<List<RepoDto>>> =
        flow {
            emit(Response.Loading())

            try {
                val response = api.getRepos(user)
                emit(Response.Success(response))
                dao.insertRepos(response.toEntity(userId))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getRepoBranches(
        user: String,
        repo: String,
        repoId: Long
    ): Flow<Response<List<BranchDto>>> =
        flow {
            emit(Response.Loading())

            try {
                val response = api.getBranches(user, repo)
                emit(Response.Success(response))
                dao.insertBranches(response.toEntity(repoId))
            } catch (e: ResponseException) {
                emit(Response.Error(getErrorMessage(e.response.status.value)))
            } catch (e: IOException) {
                emit(Response.Error(ErrorMsg.NoConnection.message))
            } catch (e: Exception) {
                emit(Response.Error(ErrorMsg.Unspecified.message))
            }
        }

    override fun getRepoCommits(
        user: String,
        repo: String,
        repoId: Long
    ): Flow<Response<List<CommitDto>>> =
        flow {
            emit(Response.Loading())

            try {
                val response = api.getCommits(user, repo)
                emit(Response.Success(response))
                dao.insertCommits(response.toEntity(repoId))
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