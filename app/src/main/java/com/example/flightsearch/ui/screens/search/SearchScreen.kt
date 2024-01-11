package com.example.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.NavigationDestination
import com.example.flightsearch.R
import com.example.flightsearch.model.Favorite

// Destino de navegación y pantalla de búsqueda principal
object SearchDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,  // Modificador para personalizar la apariencia
    onSelectCode: (String) -> Unit  // Función de clic para manejar la selección del código del aeropuerto
) {
    // Obtener la instancia del ViewModel para la pantalla de búsqueda
    val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    // Obtener el estado actual del ViewModel como un estado observable
    val uiState = viewModel.uiState.collectAsState().value

    // Columna que contiene la interfaz de usuario de la pantalla de búsqueda
    Column(modifier = modifier) {
        // Campo de texto de búsqueda con la consulta actualizada y manejo de cambios
        SearchTextField(
            uiState.searchQuery,
            onQueryChange = {
                // Actualizar la consulta en el ViewModel y restablecer el código seleccionado
                viewModel.updateQuery(it)
                viewModel.updateSelectedCode("")
                viewModel.onQueryChange(it)
            }
        )

        // Verificar si la consulta de búsqueda está vacía
        if (uiState.searchQuery.isEmpty()) {

            val favoriteList = uiState.favoriteList
            val airportList = uiState.airportList

            // Verificar si hay favoritos y mostrar la lista de favoritos o un mensaje si está vacía
            if (favoriteList.isNotEmpty()) {
                FavoriteResult(
                    airportList = airportList,
                    favoriteList = favoriteList,
                    onFavoriteClick = { departureCode: String, destinationCode: String ->
                        // Crear un objeto Favorite temporal y eliminarlo de la base de datos
                        val tmp = Favorite(
                            id = favoriteList.filter {
                                    xxx -> (xxx.departureCode == departureCode && xxx.destinationCode == destinationCode)
                            }.first().id,
                            departureCode = departureCode,
                            destinationCode = destinationCode,
                        )
                        viewModel.removeDbFavorite(tmp)
                    },
                )
            } else {
                Text(text = stringResource(R.string.text_favorites))
            }
        } else {

            val airports = uiState.airportList

            // Mostrar resultados de la búsqueda
            SearchResults(
                airports = airports,
                onSelectCode = onSelectCode
            )
        }
    }
}
