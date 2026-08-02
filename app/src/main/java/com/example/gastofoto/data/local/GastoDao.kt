package com.example.gastofoto.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {
    @Query("SELECT * FROM gastos ORDER BY fecha DESC")
    fun getAllGastos(): Flow<List<GastoEntity>>

    @Query("SELECT * FROM gastos WHERE id = :id")
    fun getGastoById(id: Int): Flow<GastoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGasto(gasto: GastoEntity)

    @Delete
    suspend fun deleteGasto(gasto: GastoEntity)
}
