package com.example.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flightsearch.model.Airport

// Composable que muestra los resultados de la búsqueda de aeropuertos
@Composable
fun SearchResults(
    modifier: Modifier = Modifier,  // Modificador para personalizar la apariencia
    airports: List<Airport>,  // Lista de aeropuertos
    onSelectCode: (String) -> Unit,  // Función de clic para manejar la selección del código del aeropuerto
) {
    // Columna perezosa que contiene la lista de aeropuertos
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Iterar sobre la lista de aeropuertos y crear una tarjeta para cada uno
        items(
            items = airports
        ) {
            // Tarjeta que contiene una fila de aeropuerto para mostrar la información
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                // Fila de aeropuerto que muestra el código y el nombre del aeropuerto
                AirportRow(
                    code = it.code,
                    name = it.name,
                    onSelectCode = onSelectCode,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
        }
    }
}
