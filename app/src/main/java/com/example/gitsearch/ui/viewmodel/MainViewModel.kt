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

    private fun getRepos(user: String) {
        viewModelScope.launch {
            repository.getUserRepos(user).collect {
                _repoList.value = it
            }
        }
    }

    private fun getCommits(user: String, repo: String) {
        viewModelScope.launch {
            repository.getRepoCommits(user, repo).collect {
                _commitList.value = it
            }
        }
    }

    private fun getBranches(user: String, repo: String) {
        viewModelScope.launch {
            repository.getRepoBranches(user, repo).collect {
                _branchList.value = it
            }
        }
    }

    fun onNavigateToUser() {
        _validUser.value = false
        //onNavigateToUser can only trigger if a valid response with data was received
        getRepos(_user.value.data!!.login)
    }

    fun onNavigateToRepo(repo: String) {
        getCommits(_user.value.data!!.login, repo)
        getBranches(_user.value.data!!.login, repo)
    }

    //a valid username is max 39 characters long alphanumeric string
    //it can contain hyphens (-) but can not start or end with a hyphen
    //it can not contain a double hyphen (--)
    fun validateUsername(username: String): Boolean {
        if (username.startsWith("-") || username.endsWith("-")) return false
        if (username.contains("--")) return false
        if (!username.matches(Regex("[a-z-]{0,38}", RegexOption.IGNORE_CASE))) return false
        return true
    }
}