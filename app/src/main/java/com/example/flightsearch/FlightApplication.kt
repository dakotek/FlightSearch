package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.UserPreferencesRepository
import com.example.flightsearch.di.AppContainer
import com.example.flightsearch.di.AppDataContainer

// Nombre de las preferencias de diseño
private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"

// Proporciona una extensión para acceder al DataStore de preferencias
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

// Clase de aplicación principal
class FlightApplication : Application() {

    // Contenedor de la aplicación para la inyección de dependencias
    lateinit var container: AppContainer

    // Repositorio de preferencias de usuario
    lateinit var userPreferencesRepository: UserPreferencesRepository

    // Método de inicialización al crear la aplicación
    override fun onCreate() {
        super.onCreate()

        // Inicializa el contenedor de la aplicación y el repositorio de preferencias de usuario
        container = AppDataContainer(this)
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}
