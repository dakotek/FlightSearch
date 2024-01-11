package com.example.flightsearch.data

import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite
import kotlinx.coroutines.flow.Flow


interface FlightRepository {
    // Métodos para obtener aeropuertos de forma asíncrona
    fun getAllAirportsFlow(): Flow<List<Airport>>
    fun getAllAirportsFlow(query: String): Flow<List<Airport>>

    // Método para obtener un aeropuerto por su código de forma asíncrona
    fun getAirportByCodeFlow(code: String): Flow<Airport>

    // Métodos para obtener aeropuertos de forma sincrónica
    suspend fun getAllAirports(): List<Airport>
    suspend fun getAllAirports(query: String): List<Airport>

    // Método para obtener un aeropuerto por su código de forma sincrónica
    suspend fun getAirportByCode(code: String): Airport

    // Método para obtener un aeropuerto por su ID de forma sincrónica
    suspend fun getAirportById(id: Int): Airport

    // Métodos para obtener vuelos favoritos de forma asíncrona
    fun getAllFavoritesFlightsFlow(): Flow<List<Favorite>>

    // Métodos para obtener vuelos favoritos de forma sincrónica
    suspend fun getAllFavoritesFlights(): List<Favorite>

    // Métodos para insertar y eliminar vuelos favoritos de forma sincrónica
    suspend fun insertFavoriteFlight(flight: Favorite)
    suspend fun deleteFavoriteFlight(flight: Favorite)

    // Método para obtener un vuelo favorito específico de forma sincrónica
    suspend fun getSingleFavorite(departureCode: String, destinationCode: String): Favorite
}