package com.example.flightsearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Clase de datos que representa un aeropuerto en la aplicación
@Entity(tableName = "airport")
data class Airport(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo("iata_code")
    val code: String = "",
    @ColumnInfo(name = "name")
    val name: String = "",
    //@ColumnInfo(name = "passengers")
    val passengers: Int = 0
)