package com.example.gastofoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gastofoto.domain.model.Gasto
import com.example.gastofoto.ui.viewmodel.GastoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleGastoScreen(
    viewModel: GastoViewModel,
    onVolver: () -> Unit
) {
    var monto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var nota by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Nuevo Gasto") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nota,
                onValueChange = { nota = it },
                label = { Text("Nota") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val montoDouble = monto.toDoubleOrNull() ?: 0.0
                    if (montoDouble > 0 && categoria.isNotBlank()) {
                        viewModel.agregarGasto(
                            Gasto(
                                monto = montoDouble,
                                categoria = categoria,
                                fecha = System.currentTimeMillis(),
                                nota = nota
                            )
                        )
                        onVolver()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Gasto")
            }
            TextButton(onClick = onVolver, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }
}
