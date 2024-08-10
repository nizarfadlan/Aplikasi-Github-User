package com.nizarfadlan.aplikasigithubuser.ui.screen.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nizarfadlan.core.data.remote.response.SearchUserResponse
import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    private val _searchUser = MutableLiveData<ResultState<SearchUserResponse>>()
    val searchUser: LiveData<ResultState<SearchUserResponse>> by lazy { _searchUser }

    init {
        getAllUsers(GITHUB_USERNAME_DEFAULT)
    }

    fun getAllUsers(username: String) {
        viewModelScope.launch {
            githubUseCase.getAllUsers(username).collect {
                _searchUser.postValue(it)
            }
        }
    }

    companion object {
        const val TAG = "HomeViewModel"
        const val GITHUB_USERNAME_DEFAULT = "nizar"
    }
}