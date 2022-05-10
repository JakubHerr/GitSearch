package com.example.gitsearch.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitsearch.data.repository.MainRepository
import com.example.gitsearch.data.util.Response
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun getUser(user: String) {
        viewModelScope.launch {
            repository.getUser(user).collect {
                if (it is Response.Loading) {
                    Log.d("Repository flow test", "Loading...")
                }
                it.data?.let { user ->
                    Log.d("Repository flow test", "Collected value: $user")
                }

                it.message?.let { error ->
                    Log.d("Repository flow test", error)
                }
            }
        }
    }

    fun getRepos(user: String, repo: String) {
        TODO()
    }

    fun getCommits(user: String, repo: String) {
        TODO()
    }

    fun getBranches(user: String, repo: String) {
        TODO()
    }
}