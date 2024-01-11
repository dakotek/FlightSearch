package com.example.flightsearch.ui.screens.flight_screen

import androidx.compose.runtime.*
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightApplication
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.model.Favorite
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ViewModel para la pantalla de resultados de vuelo
class FlightViewModel(
    savedStateHandle: SavedStateHandle,
    val flightRepository: FlightRepository
): ViewModel()  {
    // Flujo mutable que representa el estado de la interfaz de usuario
    private val _uiState = MutableStateFlow(FlightsUiState())
    val uiState: StateFlow<FlightsUiState> = _uiState

    // Código del aeropuerto obtenido de los argumentos del estado guardado
    private val airportCode: String = savedStateHandle[FlightScreenDestination.codeArg] ?: ""

    // Variable que indica si se añadió o eliminó un vuelo favorito
    var flightAdded: Boolean by mutableStateOf(false)

    init {
        // Inicialización del ViewModel
        viewModelScope.launch {
            // Procesar la lista de vuelos al iniciar el ViewModel
            processFlightList(airportCode)
        }
    }

    // Función privada para procesar la lista de vuelos
    private fun processFlightList(airportCode: String) {
        viewModelScope.launch {
            // Obtener la lista de vuelos favoritos y la lista de aeropuertos
            val favoriteFlights = flightRepository.getAllFavoritesFlights().toMutableStateList()
            val airports = flightRepository.getAllAirports().toMutableStateList()

            // Obtener el aeropuerto de salida correspondiente al código proporcionado
            val departureAirport = airports.first { it.code == airportCode }

            // Actualizar el estado de la interfaz de usuario con la nueva información
            _uiState.update {
                uiState.value.copy(
                    code = airportCode,
                    favoriteList = favoriteFlights,
                    destinationList = airports,
                    departureAirport = departureAirport,
                )
            }
        }
    }

    // Función para añadir o eliminar un vuelo favorito
    fun addFavoriteFlight(departureCode: String, destinationCode: String) {
        viewModelScope.launch {
            // Obtener el vuelo favorito correspondiente a los códigos de salida y destino
            val favorite: Favorite = flightRepository.getSingleFavorite(departureCode, destinationCode)

            // Añadir o eliminar el vuelo favorito según su existencia
            if (favorite == null) {
                val newFavorite = Favorite(
                    departureCode = departureCode,
                    destinationCode = destinationCode,
                )
                flightAdded = true
                flightRepository.insertFavoriteFlight(newFavorite)
            } else {
                flightAdded = false
                flightRepository.deleteFavoriteFlight(favorite)
            }

            // Actualizar el estado de la interfaz de usuario con la nueva lista de vuelos favoritos
            val updatedFavorites = flightRepository.getAllFavoritesFlights()
            _uiState.update {
                uiState.value.copy(
                    favoriteList = updatedFavorites,
                )
            }
        }
    }

    companion object {
        // Fábrica para crear instancias del ViewModel
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Obtener la instancia de la aplicación y el repositorio de vuelos del contenedor de dependencias
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightApplication)
                val flightRepository = application.container.flightRepository

                // Crear una nueva instancia del ViewModel con el código del aeropuerto y el repositorio de vuelos
                FlightViewModel(
                    this.createSavedStateHandle(),
                    flightRepository = flightRepository
                )
            }
        }
    }
}
