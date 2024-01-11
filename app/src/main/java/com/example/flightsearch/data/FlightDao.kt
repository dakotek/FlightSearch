package com.example.flightsearch.data

import androidx.room.*
import com.example.flightsearch.model.Airport
import com.example.flightsearch.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    // Obtener todos los favoritos ordenados por ID de forma ascendente de manera sincrónica
    @Query("SELECT * FROM favorite ORDER BY id ASC")
    suspend fun getAllFavorites(): List<Favorite>

    // Obtener todos los favoritos ordenados por ID de forma ascendente de manera asíncrona (usando Flow)
    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getAllFavoritesFlow(): Flow<List<Favorite>>

    // Obtener un favorito específico según los códigos de salida y destino de forma sincrónica
    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    suspend fun getSingleFavorite(departureCode: String, destinationCode: String): Favorite

    // Insertar o reemplazar un vuelo favorito en la base de datos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFlight(flight: Favorite)

    // Eliminar un vuelo favorito de la base de datos
    @Delete
    suspend fun deleteFavoriteFlight(flight: Favorite)

    // Obtener todos los aeropuertos ordenados por ID de forma ascendente de manera asíncrona (usando Flow)
    @Query("SELECT * FROM airport ORDER BY id ASC")
    fun getAllAirportsFlow(): Flow<List<Airport>>

    // Obtener todos los aeropuertos ordenados por ID de forma ascendente de manera sincrónica
    @Query("SELECT * FROM airport ORDER BY id ASC")
    suspend fun getAllAirports(): List<Airport>

    // Obtener aeropuertos filtrados por código IATA o nombre de forma asíncrona (usando Flow)
    @Query("SELECT * FROM airport WHERE iata_code = :query OR name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun getAllAirportsFlow(query: String): Flow<List<Airport>>

    // Obtener aeropuertos filtrados por código IATA o nombre de forma sincrónica
    @Query("SELECT * FROM airport WHERE iata_code = :query OR name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun getAllAirports(query: String): List<Airport>

    // Obtener un aeropuerto por su código de forma sincrónica
    @Query("SELECT * FROM airport WHERE iata_code = :code")
    suspend fun getAirportByCode(code: String): Airport

    // Obtener un aeropuerto por su código de forma asíncrona (usando Flow)
    @Query("SELECT * FROM airport WHERE iata_code = :code")
    fun getAirportByCodeFlow(code: String): Flow<Airport>

    // Obtener un aeropuerto por su ID de forma sincrónica
    @Query("SELECT * FROM airport WHERE id = :id")
    suspend fun getAirportById(id: Int): Airport
}