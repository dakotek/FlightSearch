package com.example.flightsearch.ui.screens.flight_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.NavigationDestination
import com.example.flightsearch.R

// Objeto que representa la pantalla de resultados de vuelo como una destino de navegación
object FlightScreenDestination : NavigationDestination {
    override val route = "flight_screen"
    override val titleRes = R.string.app_name
    const val codeArg = "code"
    val routeWithArgs = "$route/{$codeArg}"
}

// Composable que muestra la pantalla de resultados de vuelo
@Composable
fun FlightScreen() {
    // Obtener una instancia del ViewModel utilizando el Factory
    val viewModel: FlightViewModel = viewModel(factory = FlightViewModel.Factory)

    // Recolectar el estado actual del ViewModel como un estado de composición
    val uiState = viewModel.uiState.collectAsState()

    // Obtener el contexto local para mostrar toasts
    val context = LocalContext.current

    // Columna principal que contiene la vista de resultados de vuelo
    Column {
        FlightResults(
            departureAirport = uiState.value.departureAirport,
            destinationList = uiState.value.destinationList,
            favoriteList = uiState.value.favoriteList,
            // Función de clic en el ícono de favorito que interactúa con el ViewModel
            onFavoriteClick = { s1: String, s2: String ->
                viewModel.addFavoriteFlight(s1, s2)
            }
        )
    }
}
