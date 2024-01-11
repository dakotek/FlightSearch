package com.example.flightsearch

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flightsearch.ui.screens.flight_screen.FlightScreen
import com.example.flightsearch.ui.screens.flight_screen.FlightScreenDestination
import com.example.flightsearch.ui.screens.search.SearchDestination
import com.example.flightsearch.ui.screens.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp() {
    // Recuerda el controlador de navegación
    val navController = rememberNavController()

    // Scaffold es una interfaz de alto nivel para las pantallas con AppBar, Drawer, etc.
    Scaffold() { paddingValues ->
        // NavHost define las rutas y destinos de navegación
        NavHost(
            navController = navController,
            startDestination = SearchDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Composable para la pantalla de búsqueda
            composable(route = SearchDestination.route) {
                SearchScreen(
                    modifier = Modifier,
                    onSelectCode = {
                        // Navega a la pantalla de vuelos con el código de aeropuerto seleccionado
                        navController.navigate("${FlightScreenDestination.route}/${it}")
                    }
                )
            }
            // Composable para la pantalla de vuelos con argumento de código de aeropuerto
            composable(
                route = FlightScreenDestination.routeWithArgs,
                arguments = listOf(navArgument(FlightScreenDestination.codeArg) {
                    type = NavType.StringType
                })
            ) {
                FlightScreen()
            }
        }
    }
}
