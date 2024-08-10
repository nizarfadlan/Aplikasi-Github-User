package com.nizarfadlan.core.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nizarfadlan.core.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStoreSetting: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences(context: Context) {
    private val dataStore = context.dataStoreSetting
    private val themeKey = stringPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<ThemeMode> {
        return dataStore.data.map { preferences ->
            val themeString = preferences[themeKey] ?: ThemeMode.SYSTEM.name
            ThemeMode.valueOf(themeString)
        }
    }

    suspend fun saveThemeSetting(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[themeKey] = themeMode.name
        }
    }
}