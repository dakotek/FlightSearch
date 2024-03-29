package com.example.flightsearch.ui.screens.search

import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite

// Clase que representa el estado de la interfaz de usuario para la pantalla de búsqueda
data class SearchUiState(
    val searchQuery: String = "",
    val selectedCode: String = "",
    val airportList: List<Airport> = emptyList(),
    val favoriteList: List<Favorite> = emptyList(),
)