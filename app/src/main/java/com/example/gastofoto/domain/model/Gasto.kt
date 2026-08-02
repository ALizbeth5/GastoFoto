package com.example.gastofoto.domain.model

data class Gasto(
    val id: Int = 0,
    val monto: Double,
    val categoria: String,
    val fecha: Long,
    val nota: String = "",
    val fotoUri: String? = null
)
