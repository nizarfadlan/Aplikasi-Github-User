package com.nizarfadlan.aplikasigithubuser.ui.screen.detailsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nizarfadlan.core.data.remote.response.ItemsItem
import com.nizarfadlan.core.domain.common.ResultState
import com.nizarfadlan.core.domain.model.UserEntity
import com.nizarfadlan.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    private val _detailUser = MutableLiveData<ResultState<UserEntity>>()
    val detailUser: LiveData<ResultState<UserEntity>> by lazy { _detailUser }

    private val _followersUser = MutableLiveData<ResultState<List<ItemsItem>>>()
    val followersUser: LiveData<ResultState<List<ItemsItem>>> by lazy { _followersUser }

    private val _followingUser = MutableLiveData<ResultState<List<ItemsItem>>>()
    val followingUser: LiveData<ResultState<List<ItemsItem>>> by lazy { _followingUser }

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            githubUseCase.getDetailUser(username).collect {
                _detailUser.postValue(it)
            }
        }
    }

    fun getFollowers(username: String) {
        viewModelScope.launch {
            githubUseCase.getFollowers(username).collect {
                _followersUser.postValue(it)
            }
        }
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            githubUseCase.getFollowing(username).collect {
                _followingUser.postValue(it)
            }
        }
    }

    fun updateFavoriteUser(isFavorite: Boolean, username: String) {
        viewModelScope.launch {
            githubUseCase.updateFavoriteUser(isFavorite, username)
            Log.d(TAG, "updateFavoriteUser: $username")
        }
    }

    companion object {
        const val TAG = "DetailViewModel"
    }
}