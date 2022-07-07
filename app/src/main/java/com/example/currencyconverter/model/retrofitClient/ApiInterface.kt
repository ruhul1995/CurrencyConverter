package com.example.currencyconverter.model.retrofitClient

import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/latest.json?")
    suspend fun getRates(
        @Query("app_id") mAppId: String?
    ): ExchangeRatesModel
}