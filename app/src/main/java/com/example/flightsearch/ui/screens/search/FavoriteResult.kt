package com.example.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite
import com.example.flightsearch.ui.screens.flight_screen.FlightRow

// Composable que muestra la lista de vuelos favoritos
@Composable
fun FavoriteResult(
    modifier: Modifier = Modifier,  // Modificador para personalizar la apariencia
    airportList: List<Airport>,  // Lista de aeropuertos
    favoriteList: List<Favorite>,  // Lista de vuelos favoritos
    onFavoriteClick: (String, String) -> Unit  // Función de clic en el ícono de favorito
) {
    // Columna perezosa que contiene la lista de vuelos favoritos
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Iterar sobre la lista de vuelos favoritos y crear una fila para cada uno
        items(favoriteList, key = { it.id }) { item ->
            // Obtener el aeropuerto de salida y destino correspondiente a los códigos proporcionados
            val departAirport = airportList.first { airport -> airport.code == item.departureCode }
            val destinationAirport =
                airportList.first { airport -> airport.code == item.destinationCode }

            // Crear una fila de vuelo con la información del aeropuerto y la función de clic en el ícono de favorito
            FlightRow(
                isFavorite = true,
                departureAirportCode = departAirport.code,
                departureAirportName = departAirport.name,
                destinationAirportCode = destinationAirport.code,
                destinationAirportName = destinationAirport.name,
                onFavoriteClick = onFavoriteClick
            )
        }
    }
}
