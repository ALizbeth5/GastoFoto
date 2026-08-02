package com.example.gastofoto.data.repository

import com.example.gastofoto.data.local.GastoDao
import com.example.gastofoto.data.local.GastoEntity
import com.example.gastofoto.data.remote.ExchangeApiService
import com.example.gastofoto.data.remote.ExchangeResponse
import com.example.gastofoto.domain.model.Gasto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GastoRepository(
    private val gastoDao: GastoDao,
    private val apiService: ExchangeApiService
) {

    val allGastos: Flow<List<Gasto>> = gastoDao.getAllGastos().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun insert(gasto: Gasto) {
        gastoDao.insertGasto(gasto.toEntity())
    }

    suspend fun delete(gasto: Gasto) {
        gastoDao.deleteGasto(gasto.toEntity())
    }

    fun getGastoById(id: Int): Flow<Gasto?> {
        return gastoDao.getGastoById(id).map { it?.toDomain() }
    }

    suspend fun getExchangeRates(base: String): ExchangeResponse {
        return apiService.getExchangeRates(base)
    }
}

// Funciones de extensión para mapeo entre capas
fun GastoEntity.toDomain() = Gasto(id, monto, categoria, fecha, nota, fotoUri)
fun Gasto.toEntity() = GastoEntity(id, monto, categoria, fecha, nota, fotoUri)
