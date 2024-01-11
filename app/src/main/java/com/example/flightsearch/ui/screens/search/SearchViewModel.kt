package com.example.flightsearch.ui.screens.search

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightApplication
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.UserPreferencesRepository
import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ViewModel para la pantalla de búsqueda
class SearchViewModel(
    val flightRepository: FlightRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    private var deletedRecord: Favorite? = null
    private var getAirportsJob: Job? = null

    private var airportList = mutableListOf<Airport>()
    private var favoriteList = mutableListOf<Favorite>()

    // Inicialización del ViewModel
    init {
        // Observar los cambios en las preferencias del usuario y procesar la consulta de búsqueda
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect {
                processSearchQueryFlow(it.searchValue)
            }
        }
    }

    // Manejar cambios en la consulta de búsqueda
    fun onQueryChange(query: String) {
        processSearchQueryFlow(query)
    }

    // Procesar la consulta de búsqueda y actualizar el estado de la interfaz de usuario
    private fun processSearchQueryFlow(query: String) {
        _uiState.update { it.copy(searchQuery = query) }

        if (query.isEmpty()) {
            viewModelScope.launch {
                airportList = flightRepository.getAllAirports().toMutableStateList()
                favoriteList = flightRepository.getAllFavoritesFlights().toMutableStateList()
                _uiState.update {
                    uiState.value.copy(
                        airportList = airportList,
                        favoriteList = favoriteList
                    )
                }
            }
        } else {
            getAirportsJob?.cancel()

            getAirportsJob = flightRepository.getAllAirportsFlow(query)
                .onEach { result ->
                    _uiState.update {
                        uiState.value.copy(
                            airportList = result,
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }

    // Actualizar la consulta de búsqueda en las preferencias del usuario
    fun updateQuery(searchQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = searchQuery,
            )
        }
        updatePreferenceSearchValue(searchQuery)
    }

    // Actualizar el código seleccionado en el estado de la interfaz de usuario
    fun updateSelectedCode(selectedCode: String) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedCode = selectedCode,
            )
        }
    }

    // Eliminar un favorito de la base de datos y actualizar el estado de la interfaz de usuario
    fun removeDbFavorite(record: Favorite) {
        viewModelScope.launch {
            deletedRecord = record

            flightRepository.deleteFavoriteFlight(record)

            val xx = uiState.value.favoriteList.toMutableStateList()
            xx.remove(record)
            _uiState.update {
                uiState.value.copy(
                    favoriteList = xx,
                )
            }
        }
    }

    // Actualizar el valor de búsqueda en las preferencias del usuario
    fun updatePreferenceSearchValue(newValue: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateUserPreferences(searchValue = newValue)
        }
    }

    // Objeto companion para proporcionar un Factory para la creación del ViewModel
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)
                val flightRepository = application.container.flightRepository
                val preferencesRepository = application.userPreferencesRepository
                SearchViewModel(
                    flightRepository = flightRepository,
                    userPreferencesRepository = preferencesRepository
                )
            }
        }
    }
}
