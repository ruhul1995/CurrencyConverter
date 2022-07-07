package com.example.currencyconverter.di.module

import com.example.currencyconverter.repository.MyRepository
import com.example.currencyconverter.viewModel.CurrencyListViewModel
import org.koin.dsl.module

val appModule = module {
    single {
        CurrencyListViewModel(get())
    }
    single {
        MyRepository()
    }
}
