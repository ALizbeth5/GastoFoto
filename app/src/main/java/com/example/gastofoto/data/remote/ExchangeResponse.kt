package com.example.gastofoto.data.remote

data class ExchangeResponse(
    val result: String,
    val base_code: String,
    val rates: Map<String, Double>
)
