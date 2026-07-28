package com.example.gastofoto.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DetalleGastoScreen(onVolver: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Nuevo gasto") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Aquí irá el formulario de gasto (Día 2)")
            Spacer(Modifier.height(16.dp))
            Button(onClick = onVolver) { Text("Volver") }
        }
    }
}

