package com.nizarfadlan.aplikasigithubuser.ui.screen.settingScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.nizarfadlan.core.data.pref.SettingPreferences
import com.nizarfadlan.core.domain.model.ThemeMode
import kotlinx.coroutines.launch

class SettingViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<ThemeMode> = settingPreferences.getThemeSetting().asLiveData()

    fun saveThemeSetting(themeMode: ThemeMode) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(themeMode)
            Log.d(TAG, "saveThemeSetting: ${themeMode.name}")
        }
    }

    companion object {
        private const val TAG = "SettingViewModel"
    }
}