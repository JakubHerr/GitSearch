package com.example.gitsearch.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
import com.example.gitsearch.data.remote.dto.commit.CommitDto
import com.example.gitsearch.data.repository.MainRepository
import com.example.gitsearch.data.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private var _user = MutableStateFlow(Response<UserDto>())
    val user: StateFlow<Response<UserDto>> get() = _user

    private var _repoList = MutableStateFlow(Response<List<RepoDto>>())
    val repoList: StateFlow<Response<List<RepoDto>>> get() = _repoList

    private var _branchList = MutableStateFlow(Response<List<BranchDto>>())
    val branchList: StateFlow<Response<List<BranchDto>>> get() = _branchList

    private var _commitList = MutableStateFlow(Response<List<CommitDto>>())
    val commitList: StateFlow<Response<List<CommitDto>>> get() = _commitList

    //this state tells SearchScreen when to navigate to user detail
    //it is set to true when a valid, existing user was loaded
    //it is set to false during navigation
    private var _validUser = mutableStateOf(false)
    val validUser get() = _validUser


    fun getUser(user: String) {
        viewModelScope.launch {
            repository.getUser(user).collect {
                _user.value = it
                if (it is Response.Success) _validUser.value = true
            }
        }
    }

    private fun getRepos(user: String, userId: Long) {
        viewModelScope.launch {
            repository.getUserRepos(user, userId).collect {
                _repoList.value = it
            }
        }
    }

    private fun getCommits(user: String, repo: String, repoId: Long) {
        viewModelScope.launch {
            repository.getRepoCommits(user, repo, repoId).collect {
                _commitList.value = it
            }
        }
    }

    private fun getBranches(user: String, repo: String, repoId: Long) {
        viewModelScope.launch {
            repository.getRepoBranches(user, repo, repoId).collect {
                _branchList.value = it
            }
        }
    }

    //gets called to load repositories for UserDetailScreen
    fun onNavigateToUser(user: String, userId: Long) {
        _validUser.value = false
        getRepos(user, userId)
    }

    //gets called to load commits and branches for RepoDetailScreen
    fun onNavigateToRepo(user: String, repo: String, repoId: Long) {
        getCommits(user, repo, repoId)
        getBranches(user, repo, repoId)
    }

    //this function navigates to a predefined valid profile without triggering
    //navigation from SearchScreen on recomposition
    fun showAuthorProfile() {
        viewModelScope.launch {
            repository.getUser("JakubHerr").collect {
                _user.value = it
            }
        }
    }

    //a valid username is max 39 characters long alphanumeric string
    //it can contain hyphens (-) but can not start or end with a hyphen
    //it can not contain a double hyphen (--)
    fun validateUsername(username: String): Boolean {
        if (username.length > 39 || username.isEmpty()) return false
        if (username.startsWith("-") || username.endsWith("-")) return false
        if (username.contains("--")) return false
        if (!username.matches(Regex("[a-z-0-9]*", RegexOption.IGNORE_CASE))) return false
        return true
    }
}