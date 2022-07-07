package com.example.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.repository.MyRepository
import com.example.currencyconverter.viewModel.CurrencyListViewModel

class MainViewModelFactory(val repo:MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //TODO("Not yet implemented")
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java))
            return CurrencyListViewModel(repo) as T
        throw IllegalArgumentException("unknown class")
    }
}