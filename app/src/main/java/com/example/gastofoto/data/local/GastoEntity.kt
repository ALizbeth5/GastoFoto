package com.example.gastofoto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class GastoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val categoria: String,
    val fecha: Long,
    val nota: String,
    val fotoUri: String? = null
)
