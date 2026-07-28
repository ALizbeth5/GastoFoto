package com.example.gastofoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gastofoto.data.UserPreferences
import com.example.gastofoto.ui.navigation.Rutas
import com.example.gastofoto.ui.screens.AjustesScreen
import com.example.gastofoto.ui.screens.DetalleGastoScreen
import com.example.gastofoto.ui.screens.ListaGastosScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferences = UserPreferences(applicationContext)

        setContent {
            val scope = rememberCoroutineScope()
            val modoOscuro by userPreferences.modoOscuro.collectAsState(initial = false)

            MaterialTheme(
                colorScheme = if (modoOscuro) darkColorScheme() else lightColorScheme()
            ) {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Rutas.LISTA) {
                        composable(Rutas.LISTA) {
                            ListaGastosScreen(
                                onIrADetalle = { navController.navigate(Rutas.DETALLE) },
                                onIrAAjustes = { navController.navigate(Rutas.AJUSTES) }
                            )
                        }
                        composable(Rutas.DETALLE) {
                            DetalleGastoScreen(onVolver = { navController.popBackStack() })
                        }
                        composable(Rutas.AJUSTES) {
                            AjustesScreen(
                                modoOscuro = modoOscuro,
                                onCambiarModoOscuro = { activo ->
                                    scope.launch { userPreferences.guardarModoOscuro(activo) }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
