package com.nizarfadlan.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nizarfadlan.core.domain.model.UserEntity
import com.nizarfadlan.core.domain.usecase.GithubUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(private val githubUseCase: GithubUseCase) : ViewModel() {
    private val _favoriteUser = MutableLiveData<List<UserEntity>>()
    val favoriteUser: LiveData<List<UserEntity>> by lazy { _favoriteUser }

    fun getAllFavoriteUsers() {
        viewModelScope.launch {
            githubUseCase.getAllFavoriteUsers().collect {
                _favoriteUser.postValue(it)
            }
        }
    }
}