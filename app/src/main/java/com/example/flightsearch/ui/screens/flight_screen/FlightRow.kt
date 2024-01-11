package com.example.flightsearch.ui.screens.flight_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearch.data.MockData
import com.example.flightsearch.ui.screens.search.AirportRow

// Composable que representa una fila en la pantalla de resultados de vuelo
@Composable
fun FlightRow(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    departureAirportCode: String,
    departureAirportName: String,
    destinationAirportCode: String,
    destinationAirportName: String,
    onFavoriteClick: (String, String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row {
            Column(
                modifier = modifier.weight(10f)
            ) {
                Column {
                    // Información de salida
                    Text(
                        text = "Depart",
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(code = departureAirportCode, name = departureAirportName)
                    // Información de llegada
                    Text(
                        text = "Arrival",
                        modifier = Modifier.padding(start = 32.dp)
                    )
                    AirportRow(code = destinationAirportCode, name = destinationAirportName)
                }
            }
            // Icono de favorito
            IconButton(
                onClick = {
                    onFavoriteClick(departureAirportCode, destinationAirportCode)
                },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = if (isFavorite) Color.Red else Color.LightGray,
                    contentDescription = null
                )
            }
        }
    }
}

// Vista de previsualización para FlightRow
@Preview
@Composable
fun FlightRowPreview() {
    val mock = MockData
    FlightRow(
        isFavorite = true,
        departureAirportCode = mock.airports[1].code,
        departureAirportName = mock.airports[1].name,
        destinationAirportCode = mock.airports[2].code,
        destinationAirportName = mock.airports[2].name,
        onFavoriteClick = { _: String, _:String ->}
    )
}
