package com.example.flightsearch.data

import com.example.flightsearch.model.Airport

// Objeto que proporciona datos ficticios (mock data) para la aplicación de búsqueda de vuelos
object MockData {

    val airports = listOf(
        Airport(
            id = 1,
            code = "OPO",
            name = "Francisco Sá Carne Airport",
            passengers = 5053134,
        ),
        Airport(
            id = 2,
            code = "SAA",
            name = "Stockholm land Airport",
            passengers = 7494765,
        ),
        Airport(
            id = 3,
            code = "WAW",
            name = "Warsaw Chopin Airport",
            passengers = 18860000,
        ),
        Airport(
            id = 4,
            code = "MRS",
            name = "Marseille Provence Airport",
            passengers = 10151743,
        ),
        Airport(
            id = 5,
            code = "BGY",
            name = "Milan Berg Airport",
            passengers = 3833063,
        ),
    )
}