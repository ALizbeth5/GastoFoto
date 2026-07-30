package com.example.gastofoto.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GastoRepository(private val gastoDao: GastoDao) {

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
}

// Funciones de extensión para mapeo
fun GastoEntity.toDomain() = Gasto(id, monto, categoria, fecha, nota, fotoUri)
fun Gasto.toEntity() = GastoEntity(id, monto, categoria, fecha, nota, fotoUri)
