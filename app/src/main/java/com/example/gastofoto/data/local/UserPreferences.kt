package com.example.gastofoto.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")
        val MONEDA = stringPreferencesKey("moneda")
    }

    val modoOscuro: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[MODO_OSCURO] ?: false }

    val moneda: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[MONEDA] ?: "USD" }

    suspend fun guardarModoOscuro(activo: Boolean) {
        context.dataStore.edit { prefs -> prefs[MODO_OSCURO] = activo }
    }

    suspend fun guardarMoneda(moneda: String) {
        context.dataStore.edit { prefs -> prefs[MONEDA] = moneda }
    }
}
