package com.example.currencyconverter.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import com.example.currencyconverter.model.retrofitClient.ApiInterface
import com.example.currencyconverter.model.retrofitClient.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 open class MyRepository {

     val apiInterface: ApiInterface? = RetrofitInstance.retrofitInstance?.create(ApiInterface::class.java)

     suspend fun getCurrency(): ExchangeRatesModel? {
        return apiInterface?.getRates("307d4b70a47a42ff8e8a6c35d1884216")
     }
}