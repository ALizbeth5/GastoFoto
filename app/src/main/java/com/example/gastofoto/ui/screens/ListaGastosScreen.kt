package com.example.gastofoto.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.gastofoto.domain.model.Gasto
import com.example.gastofoto.ui.viewmodel.ExchangeUiState
import com.example.gastofoto.ui.viewmodel.GastoViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGastosScreen(
    viewModel: GastoViewModel,
    moneda: String,
    onIrADetalle: () -> Unit,
    onIrAAjustes: () -> Unit
) {
    val gastos by viewModel.todosLosGastos.collectAsState()
    val exchangeState by viewModel.exchangeState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Gastos") },
                actions = {
                    IconButton(onClick = onIrAAjustes) {
                        Icon(Icons.Default.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onIrADetalle) {
                Text("+", style = MaterialTheme.typography.headlineMedium)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Sección de Resumen y API
            ResumenGastos(gastos, moneda, exchangeState)

            if (gastos.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay gastos registrados. ¡Presiona +!")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(gastos) { gasto ->
                        GastoItem(gasto, moneda, onDelete = { viewModel.eliminarGasto(gasto) })
                    }
                }
            }
        }
    }
}

@Composable
fun ResumenGastos(gastos: List<Gasto>, monedaUsuario: String, state: ExchangeUiState) {
    val totalLocal = gastos.sumOf { it.monto }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Gastado", style = MaterialTheme.typography.titleSmall)
            Text("$monedaUsuario ${String.format(Locale.US, "%.2f", totalLocal)}", style = MaterialTheme.typography.headlineMedium)
            
            Spacer(modifier = Modifier.height(8.dp))
            
            when (state) {
                is ExchangeUiState.Loading -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Spacer(Modifier.width(8.dp))
                        Text("Actualizando tipos de cambio...", style = MaterialTheme.typography.bodySmall)
                    }
                }
                is ExchangeUiState.Error -> {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
                is ExchangeUiState.Success -> {
                    val rate = state.rates[monedaUsuario] ?: 1.0
                    val totalUSD = totalLocal / rate 
                    Text("Equivalente a USD ${String.format(Locale.US, "%.2f", totalUSD)}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
fun GastoItem(gasto: Gasto, moneda: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Miniatura de la foto
                if (gasto.fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(gasto.fotoUri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(16.dp))
                }

                Column {
                    Text(text = gasto.categoria, style = MaterialTheme.typography.titleMedium)
                    if (gasto.nota.isNotBlank()) {
                        Text(
                            text = gasto.nota,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            maxLines = 1
                        )
                    }
                    Text(
                        text = "$moneda ${String.format(Locale.US, "%.2f", gasto.monto)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
            }
        }
    }
}
