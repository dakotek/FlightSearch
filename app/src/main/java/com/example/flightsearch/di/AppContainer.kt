package com.example.flightsearch.di

import android.content.Context
import com.example.flightsearch.data.FlightDatabase
import com.example.flightsearch.data.FlightRepository
import com.example.flightsearch.data.OfflineFlightRepository

interface AppContainer {
    val flightRepository: FlightRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val flightRepository: FlightRepository by lazy {
        OfflineFlightRepository(FlightDatabase.getDatabase(context).flightDao())
    }
}