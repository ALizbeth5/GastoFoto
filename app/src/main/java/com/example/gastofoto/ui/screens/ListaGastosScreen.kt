package com.example.gastofoto.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGastosScreen(
    onIrADetalle: () -> Unit,
    onIrAAjustes: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis gastos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onIrADetalle) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Aquí irá la lista de gastos (Día 2)")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onIrAAjustes) { Text("Ir a Ajustes") }
        }
    }
}


