package com.rifqi.githubuserapp.settingpref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    fun getTheme(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            pref[themeKey] ?: false
        }
    }

    suspend fun saveTheme(isDarkActive: Boolean) {
        dataStore.edit { pref ->
            pref[themeKey] = isDarkActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreferences {
            return INSTANCE ?: synchronized(this) {

                val instance = ThemePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }


}