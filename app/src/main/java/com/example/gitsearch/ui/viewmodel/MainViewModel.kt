package com.example.gitsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.remote.dto.BranchDto
import com.example.gitsearch.data.remote.dto.CommitDto
import com.example.gitsearch.data.remote.dto.RepoDto
import com.example.gitsearch.data.remote.dto.UserDto
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


    fun getUser(user: String) {
        viewModelScope.launch {
            repository.getUser(user).collect {
                _user.value = it
            }
        }
    }

    fun getRepos(user: String) {
        viewModelScope.launch {
            repository.getUserRepos(user).collect {
                _repoList.value = it
            }
        }
    }

    fun getCommits(user: String, repo: String) {
        viewModelScope.launch {
            repository.getRepoCommits(user, repo).collect {
                _commitList.value = it
            }
        }
    }

    fun getBranches(user: String, repo: String) {
        viewModelScope.launch {
            repository.getRepoBranches(user, repo).collect {
                _branchList.value = it
            }
        }
    }
}