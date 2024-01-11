package com.example.flightsearch.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// Composable que representa una fila para mostrar información de un aeropuerto
@Composable
fun AirportRow(
    modifier: Modifier = Modifier,  // Modificador para personalizar la apariencia
    code: String,  // Código del aeropuerto
    name: String,  // Nombre del aeropuerto
    onSelectCode: (String) -> Unit = { },  // Función de clic para manejar la selección del código del aeropuerto
) {
    // Fila que contiene información del aeropuerto y es cliclable
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                onClick = {
                    if (code != "") {
                        onSelectCode(code)
                    }
                },
            )
    ) {
        // Espaciador a la izquierda para mejorar la apariencia
        Spacer(
            modifier = Modifier.width(24.dp)
        )
        // Texto con el código del aeropuerto y fuente en negrita
        Text(
            text = code,
            fontWeight = FontWeight.Bold
        )
        // Espaciador entre el código y el nombre
        Spacer(modifier = Modifier.width(16.dp))
        // Texto con el nombre del aeropuerto, con un máximo de 1 línea y truncamiento de texto si es necesario
        Text(
            text = name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
