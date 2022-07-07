package com.example.currencyconverter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.model.dataClasses.ExchangeRatesModel
import com.example.currencyconverter.repository.MyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class CurrencyListViewModel(var myRepository: MyRepository): ViewModel() {

    private var exchangeRatesList: MutableLiveData<ExchangeRatesModel> = MutableLiveData()

    val exchangeRateLiveData: LiveData<ExchangeRatesModel>
    get() = exchangeRatesList


    fun apiCall()
    {
        viewModelScope.launch(Dispatchers.IO) {
            // Make the network call and suspend execution until it finishes
             try {
                exchangeRatesList.postValue(myRepository.getCurrency())
            }
            catch (e : Exception){
                exchangeRatesList.postValue(null)
                Result.Error(Exception("Network request failed"))
            }
        }
    }
}