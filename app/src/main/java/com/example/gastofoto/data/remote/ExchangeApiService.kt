package com.example.gastofoto.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeApiService {
    @GET("v6/latest/{base}")
    suspend fun getExchangeRates(@Path("base") base: String): ExchangeResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://open.er-api.com/"

    val api: ExchangeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApiService::class.java)
    }
}
