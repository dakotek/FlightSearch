package com.example.flightsearch.ui.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.flightsearch.R

// Composable que muestra un campo de texto para realizar búsquedas
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    query: String,  // Consulta actual en el campo de texto
    onQueryChange: (String) -> Unit  // Función para manejar cambios en la consulta
) {
    // Obtener el administrador de enfoque local
    val focusManager = LocalFocusManager.current

    // TextField que permite al usuario ingresar la consulta de búsqueda
    TextField(
        value = query,
        onValueChange = { onQueryChange(it) },
        placeholder = { Text(text = stringResource(R.string.text_search)) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
    )
}
