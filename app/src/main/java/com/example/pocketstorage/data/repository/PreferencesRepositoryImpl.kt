package com.example.pocketstorage.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pocketstorage.core.utils.LOCATION_ID_KEY
import com.example.pocketstorage.core.utils.SETTINGS_PREFERENCES
import com.example.pocketstorage.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS_PREFERENCES
)

class PreferencesRepositoryImpl @Inject constructor(
    private val context: Context
) : PreferencesRepository {
    override suspend fun saveLocationIdToDataStorage(buildingId: String) {
        val preferenceKey = stringPreferencesKey(LOCATION_ID_KEY)
        context.dataStore.edit {pref ->
            pref[preferenceKey] = buildingId
        }
    }

    override suspend fun getLocationIdFromDataStorage(): Flow<String?> {
        val preferenceKey = stringPreferencesKey(LOCATION_ID_KEY)

        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[preferenceKey]
            }
    }
}