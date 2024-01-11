package com.example.flightsearch.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.flightsearch.data.UserPreferencesKeys.SEARCH_VALUE
import kotlinx.coroutines.flow.*
import java.io.IOException

// Clase de datos que representa las preferencias del usuario
data class UserPreferences(
    val searchValue: String = "",
)

// Objeto que contiene claves para las preferencias del usuario
object UserPreferencesKeys {
    val SEARCH_VALUE = stringPreferencesKey("search_value")
}

// Repositorio que gestiona y proporciona acceso a las preferencias del usuario utilizando DataStore
class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {

    // Método para actualizar las preferencias del usuario de forma sincrónica
    suspend fun updateUserPreferences(searchValue: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_VALUE] = searchValue
        }
    }

    // Flujo que emite cambios en las preferencias del usuario
    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // Manejar excepciones, como problemas de lectura/escritura
            if (exception is IOException) {
                // Tratar errores de E/S, si es necesario
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Mapear preferencias de DataStore a objetos UserPreferences
            UserPreferences(
                searchValue = preferences[SEARCH_VALUE] ?: "",
            )
        }
}