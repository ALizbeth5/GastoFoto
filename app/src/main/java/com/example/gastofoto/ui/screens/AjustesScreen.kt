package com.example.gastofoto.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesScreen(
    modoOscuro: Boolean,
    onCambiarModoOscuro: (Boolean) -> Unit,
    moneda: String,
    onCambiarMoneda: (String) -> Unit
) {
    val opcionesMoneda = listOf("USD", "PEN", "EUR")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ajustes") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo oscuro")
                Switch(checked = modoOscuro, onCheckedChange = onCambiarModoOscuro)
            }

            HorizontalDivider()

            Column {
                Text("Moneda preferida", style = MaterialTheme.typography.titleMedium)
                opcionesMoneda.forEach { opcion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (opcion == moneda),
                            onClick = { onCambiarMoneda(opcion) }
                        )
                        Text(text = opcion, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}
